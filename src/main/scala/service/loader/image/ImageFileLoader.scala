package service.loader.image


/**
 * A specialized trait for loading images from file paths.
 *
 * This trait extends the `ImageLoader` interface, specializing it to handle input sources
 * specified as `String` file paths, enabling the loading of images from the filesystem.
 */
trait ImageFileLoader extends ImageLoader[String] {
  
}
