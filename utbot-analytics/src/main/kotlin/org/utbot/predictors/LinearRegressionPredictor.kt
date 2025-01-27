package org.utbot.predictors

import org.utbot.analytics.MLPredictor
import org.utbot.framework.PathSelectorType
import org.utbot.framework.UtLogging
import org.utbot.framework.UtSettings
import org.utbot.predictors.util.PredictorLoadingException
import org.utbot.predictors.util.WeightsLoadingException
import org.utbot.predictors.util.splitByCommaIntoDoubleArray
import smile.math.MathEx.dot
import smile.math.matrix.Matrix
import java.io.File

private const val DEFAULT_WEIGHT_PATH = "linear.txt"

private val logger = UtLogging.logger {}

/**
 * Last weight is bias
 */
private fun loadWeights(path: String): Matrix {
    val weightsFile = File("${UtSettings.modelPath}/${path}")
    lateinit var weightsArray: DoubleArray

    try {
        if (!weightsFile.exists()) {
            error("There is no file with weights with path: ${weightsFile.absolutePath}")
        }

        weightsArray = weightsFile.readText().splitByCommaIntoDoubleArray()
    } catch (e: Exception) {
        throw WeightsLoadingException(e)
    }

    return Matrix(weightsArray)
}

class LinearRegressionPredictor(weightsPath: String = DEFAULT_WEIGHT_PATH, scalerPath: String = DEFAULT_SCALER_PATH) :
    MLPredictor {
    private lateinit var weights: Matrix
    private lateinit var scaler: StandardScaler

    init {
        try {
            weights = loadWeights(weightsPath)
            scaler = loadScaler(scalerPath)
        } catch (e: PredictorLoadingException) {
            logger.info(e) {
                "Error while initialization of LinearRegressionPredictor. Changing pathSelectorType on INHERITORS_SELECTOR"
            }
            UtSettings.pathSelectorType = PathSelectorType.INHERITORS_SELECTOR
        }
    }

    fun predict(input: List<List<Double>>): List<Double> {
        // add 1 to each feature vector
        val matrixValues = input
            .map { (it + 1.0).toDoubleArray() }
            .toTypedArray()

        val X = Matrix(matrixValues)

        return X.mm(weights).col(0).toList()
    }

    override fun predict(input: List<Double>): Double {
        var inputArray =  Matrix(input.toDoubleArray()).sub(scaler.mean).div(scaler.variance).col(0)
        inputArray += 1.0

        return dot(inputArray, weights.col(0))
    }
}