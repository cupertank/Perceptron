package cupertank.perceptron.serializers

import kotlinx.serialization.*

@Serializable
data class Config(
    val layers: List<LayerType>
)

@Serializable
sealed class LayerType

@Serializable
@SerialName("Dense")
data class Dense(
    val config: LayerConfig
) : LayerType()

@Serializable
data class LayerConfig(
    val name: String,
    @SerialName("batch_input_shape")
    val batchInputShape: List<Int?> = emptyList(),
    val dtype: String = "",
    val units: Int,
    val activation: String,
    @SerialName("use_bias")
    val useBias: Boolean,
    val trainable: Boolean = false
)

@Serializable
data class Weights(
    @SerialName("weights")
    val layersParameters: List<LayerParameters>
)

@Serializable
data class LayerParameters(
    val name: String,
    @SerialName("data")
    val layerData: LayerData
)

@Serializable
data class LayerData(
    val kernel: List<List<Double>>,
    val bias: List<Double> = emptyList()
)