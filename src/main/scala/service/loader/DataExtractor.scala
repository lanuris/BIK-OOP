package service.loader

/**
 * A generic trait for extracting data from a given input of type `T` and returning a result of type `R`.
 *
 * @tparam T the type of the input data (e.g., a buffered image or other source data)
 * @tparam R the type of the extracted result
 */
trait DataExtractor [T, R]{

  /**
   * Extracts data from the provided input and returns the result.
   *
   * @param buffered the input data of type `T` to extract information from
   * @return the extracted data of type `R`
   */
  def extract(buffered: T): R
}
