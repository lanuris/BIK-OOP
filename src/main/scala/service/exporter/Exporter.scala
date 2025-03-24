package service.exporter

/**
 * A generic trait for exporting data to a file.
 *
 * This trait defines a single method, `exportFile`, which takes an item of type `T`
 * and exports it to a file. The specific export logic depends on the concrete implementation.
 *
 * @tparam T the type of the item to be exported
 */
trait Exporter [T] {

  /**
   * Exports the given item to a file.
   *
   * @param item the item of type `T` to be exported
   * @throws Exception if an error occurs during the export process
   */
  def exportFile (item: T): Unit
}
