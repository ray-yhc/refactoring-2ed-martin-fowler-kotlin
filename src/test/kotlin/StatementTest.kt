import domain.Invoice
import domain.Performance
import domain.Play
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe

class StatementTest : AnnotationSpec() {
    @Test
    fun `연극 청구 내역을 텍스트 형식으로 출력한다`() {
        // given
        val invoice = Invoice(
            "BigCo",
            listOf(
                Performance("hamlet", 55),
                Performance("as-like", 35),
                Performance("othello", 40)
            )
        )
        val plays = mapOf(
            "hamlet" to Play("Hamlet", "tragedy"),
            "as-like" to Play("As You Like It", "comedy"),
            "othello" to Play("Othello", "tragedy")
        )
        val expected = """
            청구내역 (고객명: BigCo)
            Hamlet: $650.00 55석
            As You Like It: $580.00 35석
            Othello: $500.00 40석
            총액: $1730.00
            적립 포인트: 47 점
        """.trimIndent()

        // when
        val printString = statement(invoice, plays)

        // then
        printString shouldBe expected
    }

    @Test
    fun `연극 청구 내역을 html 형식으로 출력한다`() {
        // given
        val invoice = Invoice(
            "BigCo",
            listOf(
                Performance("hamlet", 55),
                Performance("as-like", 35),
                Performance("othello", 40)
            )
        )
        val plays = mapOf(
            "hamlet" to Play("Hamlet", "tragedy"),
            "as-like" to Play("As You Like It", "comedy"),
            "othello" to Play("Othello", "tragedy")
        )
        val expected = """
            <h1>청구내역 (고객명: BigCo)</h1>
            <table>
            <tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>
            <tr><td>Hamlet</td><td>55</td><td>$650.00</td></tr>
            <tr><td>As You Like It</td><td>35</td><td>$580.00</td></tr>
            <tr><td>Othello</td><td>40</td><td>$500.00</td></tr>
            </table>
            <p>총액: <em>$1730.00</em></p>
            <p>적립 포인트: <em>47</em>점</p>
        """.trimIndent()

        // when
        val printString = htmlStatement(invoice, plays)

        // then
        printString shouldBe expected
    }
}