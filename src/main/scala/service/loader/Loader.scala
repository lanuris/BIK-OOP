package service.loader

/**
 * A generic trait for loading resources or data.
 *
 * @tparam R the type of the resource or data being loaded
 */
trait Loader [R]{

  /**
   * Performs the loading operation and returns the resource or data.
   *
   * @return the loaded resource or data of type `R`
   */
  def load(): R
}
