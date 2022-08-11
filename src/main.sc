require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.

    state: Hello
        intent!: /привет
        a: Привет привет

    state: Bye
        intent!: /пока
        a: Пока пока

    state: NoMatch
        event!: noMatch
        script:
            $response.replies = $response.replies || [];
            $response.replies
                 .push({
                    type:"switch",
                    closeChatPhrases: "Сбросить оператора",
                    firstMessage: "Первое сообщение",
                    phoneNumber: "01234567",
            });

    state: Match
        event!: match
        a: {{$context.intent.answer}}