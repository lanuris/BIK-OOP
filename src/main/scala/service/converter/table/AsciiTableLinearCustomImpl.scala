package service.converter.table

/**
 * A custom implementation of `AsciiTable` that extends `AsciiTableLinearImpl` to use a custom
 * character set for mapping greyscale intensity values to ASCII characters.
 *
 * This class inherits the linear distribution logic from `AsciiTableLinearImpl` but allows the use
 * of a user-provided string of ASCII characters for the mapping. The characters should be ordered
 * from the darkest (`customCharacters(0)`) to the brightest (`customCharacters(customCharacters.length - 1)`).
 *
 * @param customCharacters a custom string containing ASCII characters, ordered by intensity (dark to light)
 */
class AsciiTableLinearCustomImpl (customCharacters: String) extends AsciiTableLinearImpl (customCharacters) {
    // Inherits the linear conversion logic
}
