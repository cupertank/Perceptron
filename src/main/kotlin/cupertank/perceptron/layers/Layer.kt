package cupertank.perceptron.layers

import cupertank.perceptron.serializers.Dense
import cupertank.perceptron.serializers.LayerData
import cupertank.perceptron.serializers.LayerType
import koma.matrix.*

class Layer(layerType: LayerType, layerData: LayerData) {
    val name : String
    val units : Int
    val activation : String
    val useBias : Boolean
    val inputType : String
    val batchInputShape: List<Int?>

    init {
        when(layerType) {
            is Dense -> {
                name = layerType.config.name
                units = layerType.config.units
                activation = layerType.config.activation
                useBias = layerType.config.useBias
                inputType = layerType.config.dtype
                batchInputShape = layerType.config.batchInputShape
            }
        }

        if (layerData.kernel.isEmpty() ||
            layerData.kernel.any { it.size != units })
            throw Exception("Wrong kernel input")
    }


    val matrix = Matrix(
        layerData.kernel.size,
        layerData.kernel[0].size
    ) {x, y -> layerData.kernel[x][y]}

    val bias: Matrix<Double>? = if (useBias){
        if (layerData.bias.isEmpty())
            throw Exception("Wrong bias input")

        Matrix(layerData.bias.size,1) { x, _ -> layerData.bias[x] }
    } else
        null
}