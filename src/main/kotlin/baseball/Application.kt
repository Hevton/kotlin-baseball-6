package baseball

import camp.nextstep.edu.missionutils.Console
import camp.nextstep.edu.missionutils.Randoms

fun main() {

    var play = true // 게임 진행 결정값

    val computerList = mutableListOf<Int>()
    val playerList = mutableListOf<Int>()
    var playerString: String?

    println("숫자 야구 게임을 시작합니다.")

    while (play) {
        generateComputerList(computerList)

        while (true) {
            print("숫자를 입력해주세요 : ")
            playerString = Console.readLine()
            playerString?.let {

                playerList.clear()

                // 세자리가 아닌 예외
                if (it.length != 3) throw IllegalArgumentException()
                for (index in it.indices) {
                    // 숫자가 아닌 예외
                    if (it[index] < '1' || it[index] > '9') throw IllegalArgumentException()
                    // 숫자가 중복된 예외
                    if (playerList.contains(it[index] - '0')) throw IllegalArgumentException()
                    // 정수 리스트에 넣어주기
                    playerList.add(it[index] - '0')
                }
            } ?: throw IllegalArgumentException() // null인 예외


            // 게임 결과 분기
            if (!compareList(computerList, playerList)) {
                // 정답을 못찾았다. 계속해서 맞추기
                continue
            } else {
                // 3 스트라이크. 게임 끝.

                println("3개의 숫자를 모두 맞히셨습니다! 게임 종료")
                println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.")

                playerString = Console.readLine()

                // 길이가 1이 아닌 예외
                if (playerString == null || playerString.length != 1) throw IllegalArgumentException()
                // 숫자가 아닌 예외
                if (playerString[0] < '1' || playerString[0] > '9') throw IllegalArgumentException()

                // 재시작 여부 값이 올바름
                play = (playerString[0] == '1')
                break // while(true) 종료`
            }
        }
    }
}

// 컴퓨터의 숫자 뽑기 함수
fun generateComputerList(computerList: MutableList<Int>) {
    // 초기화
    computerList.clear()
    // 생성
    while (computerList.size < 3) {
        val randomNumber = Randoms.pickNumberInRange(1, 9)
        if (randomNumber !in computerList) {
            computerList.add(randomNumber)
        }
    }
}

// 게임 결과 처리 함수
fun compareList(computerList: List<Int>, playerList: List<Int>): Boolean {

    var result = "" // 결과
    var strikes = 0 // 스트라이크
    var balls = 0 // 볼

    for (i in computerList.indices) {
        if (computerList[i] == playerList[i]) {
            strikes++
        } else if (computerList.contains(playerList[i])) {
            balls++
        }
    }

    result = if (balls > 0 && strikes > 0)
        "${balls}볼 ${strikes}스트라이크"
    else if (balls > 0)
        "${balls}볼"
    else if (strikes > 0)
        "${strikes}스트라이크"
    else
        "낫싱"

    println(result)
    return strikes == 3
}






