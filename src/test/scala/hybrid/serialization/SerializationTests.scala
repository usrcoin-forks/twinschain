package hybrid.serialization

import examples.curvepos.transaction.PublicKey25519NoncedBox
import examples.hybrid.blocks.{PosBlock, PowBlock}
import examples.hybrid.history.HybridSyncInfo
import examples.hybrid.state.SimpleBoxTransaction
import hybrid.HybridGenerators
import org.scalatest.prop.{GeneratorDrivenPropertyChecks, PropertyChecks}
import org.scalatest.{Matchers, PropSpec}
import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.core.transaction.wallet.WalletBox

class SerializationTests extends PropSpec
with PropertyChecks
with GeneratorDrivenPropertyChecks
with Matchers
with HybridGenerators {

  property("WalletBox serialization") {
    forAll(walletBoxGen) { b: WalletBox[PublicKey25519Proposition, PublicKey25519NoncedBox] =>
      val parsed = WalletBox.parse[PublicKey25519Proposition, PublicKey25519NoncedBox](WalletBox.bytes(b))(PublicKey25519NoncedBox.parseBytes).get
      WalletBox.bytes(parsed) shouldEqual WalletBox.bytes(b)
    }
  }

  property("PosBlock serialization") {
    forAll(posBlockGen) { b: PosBlock =>
      val parsed = b.companion.parse(b.bytes).get
      parsed.bytes shouldEqual b.bytes
    }
  }

  property("PowBlock serialization") {
    forAll(powBlockGen) { b: PowBlock =>
      val parsed = b.companion.parse(b.bytes).get
      assert(parsed.brothersCount == b.brothersCount)
      assert(parsed.brothersHash sameElements b.brothersHash)
      assert(parsed.brothers.headOption.map(ph => ph.brothersHash sameElements b.brothers.head.brothersHash).getOrElse(true))
      parsed.bytes shouldEqual b.bytes
    }
  }

  property("SimpleBoxTransaction serialization") {
    forAll(simpleBoxTransactionGen) { b: SimpleBoxTransaction =>
      val parsed = b.companion.parse(b.bytes).get
      parsed.bytes shouldEqual b.bytes
    }
  }

  property("HybridSyncInfo serialization") {
    forAll(hybridSyncInfoGen) { b: HybridSyncInfo =>
      val parsed = HybridSyncInfo.parse(b.bytes).get
      parsed.bytes shouldEqual b.bytes
    }
  }

}
