package com.example.kcp

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.irBlock
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irReturnUnit
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrReturn
import org.jetbrains.kotlin.platform.JsPlatform
import org.jetbrains.kotlin.platform.TargetPlatform
import org.jetbrains.kotlin.util.Logger

class AllowTestReturnExtension(private val logger: Logger) : IrGenerationExtension {

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        val platform: TargetPlatform? = pluginContext.platform
        if (platform?.componentPlatforms?.none { it is JsPlatform } == true) { // only native targets have problems with returns from test classes
            moduleFragment.accept(AllowTestReturnIrVisitor(logger, pluginContext), null)
        }
    }

}

@Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE", "unused")
class AllowTestReturnIrVisitor(private val logger: Logger, private val pluginContext: IrPluginContext) :
    IrElementTransformerVoidWithContext() {

    private val typeUnit = pluginContext.irBuiltIns.unitType

    override fun visitFunctionNew(function: IrFunction): IrStatement {
        function.body ?: return super.visitFunctionNew(function)
        function.body = returnUnit(function)
        function.returnType = typeUnit
        return super.visitFunctionNew(function)
    }

    private fun returnUnit(function: IrFunction): IrBody {
        return DeclarationIrBuilder(pluginContext, function.symbol).irBlockBody {
            return function.body!!.transform(ReturnsToUnitTransformer(function, pluginContext), null)
        }
    }

    private class ReturnsToUnitTransformer(
        private val function: IrFunction,
        private val pluginContext: IrPluginContext,
    ) : IrElementTransformerVoidWithContext() {
        override fun visitReturn(returnExpression: IrReturn): IrExpression {
            // ignore irrelevant returns (return@let, return@launch, etc.)
            if (returnExpression.returnTargetSymbol != function.symbol) {
                return returnExpression
            }

            return DeclarationIrBuilder(
                pluginContext,
                function.symbol,
                returnExpression.startOffset,
                returnExpression.endOffset
            ).irBlock {
                +returnExpression.value  // transformation itself: swap places return and expression-in-return
                +DeclarationIrBuilder(
                    pluginContext,
                    function.symbol,
                    returnExpression.startOffset,
                    returnExpression.endOffset
                ).irReturnUnit()
            }
        }
    }

}