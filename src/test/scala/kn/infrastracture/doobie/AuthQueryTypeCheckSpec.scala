package kn.infrastracture.doobie

import cats.effect.IO
import doobie.scalatest.IOChecker
import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks
import kn.arbitrary.UserArbitrary._
import tsec.mac.jca.HMACSHA256
import tsec.authentication.AugmentedJWT
import tsec.common.SecureRandomId
import org.scalatest.matchers.should.Matchers

class AuthQueryTypeCheckSpec
  extends AnyFunSuite
    with Matchers
    with ScalaCheckPropertyChecks
    with IOChecker {
  override def transactor: doobie.Transactor[IO] = testTransactor

  import kn.infrastructure.doobie.AuthSQL._

  test("Type check auth queries") {
    forAll { jwt: AugmentedJWT[HMACSHA256, Long] => check(insert(jwt)) }
    forAll { jwt: AugmentedJWT[HMACSHA256, Long] => check(update(jwt)) }
    forAll { id: SecureRandomId => check(select(id)) }
    forAll { id: SecureRandomId => check(delete(id)) }
  }
}
