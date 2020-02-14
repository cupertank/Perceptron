import cupertank.perceptron.Perceptron
import kotlin.random.Random

fun main(){
    val perceptron = Perceptron()
    val input = List(463) { Random.nextDouble(-1.0, 1.0) }
    print(perceptron.prediction(input))
}