package kn.infrastructure.doobie

import doobie._
import doobie.implicits._
import doobie.util.pos.Pos

/**
  * Pagination is a convenience to simply add limits and offsets to any query
  * Part of the motivation for this is using doobie's typechecker, which fails
  * unexpectedly for H2. H2 reports it requires a VARCHAR for limit and offset,
  * which seems wrong.
  */
trait SQLPagination {
  def limit[A: Read](lim: Int)(q: Query0[A])(implicit pos: Pos): Query0[A] =
    (q.toFragment ++ fr" limit $lim").query

  def paginate[A: Read](lim: Int, offset: Int)(q: Query0[A])(implicit pos: Pos): Query0[A] =
    (q.toFragment ++ fr" limit $lim offset $offset").query
}

object SQLPagination extends SQLPagination
