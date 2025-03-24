package service.filter

/**
 * A generic trait for applying a filter to an item of type `T`.
 *
 * This trait defines a single method, `applyFilter`, which applies some filtering
 * logic to the provided item and returns the filtered result.
 *
 * @tparam T the type of the item to be filtered
 */
trait Filter [T] {

  /**
   * Applies a filter to the given item and returns the filtered result.
   *
   * @param item the item of type `T` to which the filter is applied
   * @return the filtered item of type `T`
   */
  def applyFilter (item: T): T

}
