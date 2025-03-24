package service.exporter.text

/**
 * A concrete implementation of `StreamTextExporter` for exporting text to the standard output.
 *
 * This class is a specialized version of `StreamTextExporter` that writes text directly to the
 * standard output stream (`System.out`). It inherits all functionality from `StreamTextExporter`,
 * including resource management and UTF-8 encoding.
 */
class StdOutputExporter extends StreamTextExporter(System.out){

}
