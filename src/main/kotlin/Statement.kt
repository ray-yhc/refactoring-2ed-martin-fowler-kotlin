import domain.Invoice
import domain.Play

class Statement

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    var totalAmount: Int = 0
    var volumeCredit: Int = 0
    val result = StringBuilder("청구내역 (고객명: ${invoice.customer})\n")

    for (performance in invoice.performances) {
        val play = plays[performance.playID] ?: throw Exception("알 수 없는 장르")
        var thisAmount = 0

        when (play.type) {
            "tragedy" -> {
                thisAmount = 40000
                if (performance.audience > 30) {
                    thisAmount += 1000 * (performance.audience - 30)
                }
            }

            "comedy" -> {
                thisAmount = 30000
                if (performance.audience > 20) {
                    thisAmount += 10000 + 500 * (performance.audience - 20)
                }
                thisAmount += 300 * performance.audience
            }

            else -> throw Exception("알 수 없는 장르: ${play.type}")
        }

        // 포인트를 적립한다.
        volumeCredit += maxOf(performance.audience - 30, 0)

        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if (play.type == "comedy") volumeCredit += (performance.audience / 5)

        // 청구 내역을 출력한다.
        result.append("${play.name}: ${String.format("$%.2f", thisAmount / 100.0)} ${performance.audience}석\n")
        totalAmount += thisAmount
    }

    result.append("총액: ${String.format("$%.2f", totalAmount / 100.0)}\n")
    result.append("적립 포인트: $volumeCredit 점")
    return result.toString()
}

fun htmlStatement(invoice: Invoice, plays: Map<String, Play>): String {
    // todo: Ch 1.6, p.60 코드 참고
    return "";
}