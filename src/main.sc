require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Начнём.
        
    state: Hello
        intent!: /привет
        a: Добрый день, человек!

    state: NoMatch
        event!: noMatch
        a: Переводим на оператора...
        script:
            $response.replies = $response.replies || [];
            $response.replies
                 .push({
                    type:"switch"
            });
            
    state: Group2
        q!: группа 2
        a: Переводим на Вторую линюю...
        script:
            $response.replies = $response.replies || [];
            $response.replies
                 .push({
                    type:"switch",
                    destination: 72
            });            
            
    state: LivechatFinished
        event!: livechatFinished
        a: Переводим обратно на бота...
        go!: /NPS
    
    state: testNPS
        q!: nps
        go!: /NPS
        
    state: NPS
        a: Пожалуйста, оцените качество обслуживания:
        buttons:
            "👍" -> ./good
            "👎" -> ./bad
        
        state: good
            a: Рады, что смогли вам помочь!
            
        state: bad
            a: Спасибо за отзыв! Будем стараться улучшить качество обслуживания!
            

    state: Match
        event!: match
        a: {{$context.intent.answer}}