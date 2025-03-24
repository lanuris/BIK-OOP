package service.converter

/**
 * A generic trait for converting data from one type to another.
 *
 * This trait defines a single method, `convert`, which transforms an input of type `T` into an output of type `R`.
 *
 * @tparam T the type of the input data
 * @tparam R the type of the output data
 */
trait Converter [T,R] {

  /**
   * Converts the input data of type `T` into an output of type `R`.
   *
   * @param data the input data to be converted
   * @return the converted data of type `R`
   */
  def convert(data:T): R

}
