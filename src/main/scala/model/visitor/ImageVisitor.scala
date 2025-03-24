package model.visitor

import model.Image

trait ImageVisitor [T,R]{

  def visitImage(image: Image[T]): R
}
