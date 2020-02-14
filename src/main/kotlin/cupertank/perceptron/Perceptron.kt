package cupertank.perceptron

import cupertank.perceptron.layers.*
import cupertank.perceptron.serializers.*
import koma.extensions.*
import koma.matrix.*
import kotlinx.serialization.json.*
import kotlin.math.*

class Perceptron(configText: String, weightsText: String){
    private val layers: List<Layer>
    val inputType: String
    val batchInputShape: List<Int?>

    init {
        val json = Json(JsonConfiguration.Stable.copy(classDiscriminator = "class_name"))
        val config = json.parse(Config.serializer(), configText)
        val weights = json.parse(Weights.serializer(), weightsText)


        if (config.layers.isEmpty())
            throw Exception("Layers not found in config")

        if (weights.layersParameters.isEmpty())
            throw Exception("Layers not found in weights")

        layers = List(config.layers.size) { index ->
            Layer(
                config.layers[index],
                weights.layersParameters[index].layerData
            )
        }

        inputType = layers[0].inputType
        batchInputShape = layers[0].batchInputShape
    }

    constructor():
            this(object {}.javaClass.getResource("/config.json").readText(),
                object {}.javaClass.getResource("/weights.json").readText())

    fun prediction(input: List<Double>): Double{
        if (input.size !in batchInputShape)
            throw Exception("Wrong vector length")

        var inputVector = Matrix(input.size, 1) { x, _ -> input[x] }

        for (layer in layers){
            val transposedMatrix = layer.matrix.transpose()
            inputVector = transposedMatrix * inputVector
            if (layer.useBias)
                inputVector += layer.bias ?: throw Exception("Wrong input bias")
            inputVector = when(layer.activation){
                "relu" -> inputVector.map { x -> max(0.0, x) }
                "sigmoid" -> inputVector.map { x -> 1.0 / (1.0 + exp(-x)) }
                else -> throw Exception("Unknown activation")
            }
        }
        return inputVector[0, 0]
    }
}
