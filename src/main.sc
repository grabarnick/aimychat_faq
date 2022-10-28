require: slotfilling/slotFilling.sc
  module = sys.zb-common
theme: /

    state: Start
        q!: $regex</start>
        a: Вас приветствует служба поддержки! 
        a: Введите, пожалуйста, ваш вопрос:
        
    state: Hello
        intent!: /привет
        a: Введите, пожалуйста, ваш вопрос:
    
    state: HR
        intent!: /HR служба
        a: HR служба на связи! Сейчас переведём на оператора
        script:
            $response.replies = $response.replies || [];
            $response.replies
                 .push({
                    type:"switch",
                    destination: 15,
                    theme: $context.intent,
                    attributes: {                         // пречат поля
                        "Имя": "Иван",
                        "Фамилия": "Петров",
                        "Должность:": "Курьер"
                        
                    }
            });
        
        state: Увольнение
            intent!: /HR служба/хочу уволиться
            a: Подождите немного, сейчас с вами свяжется наш сотрудник.
            script:
                $response.replies = $response.replies || [];
                $response.replies
                     .push({
                        type:"switch",
                        destination: 15,
                        theme: "Увольнение",
                        attributes: {                         // пречат поля
                            "Имя": "Иван",
                            "Фамилия": "Петров",
                            "Должность:": "Курьер"
                            
                        }
                });            
            
    state: IT
        intent!: /IT поддержка
        a: HR служба приветствует вас! Сейчас переведём на оператора
        script:
            $response.replies = $response.replies || [];
            $response.replies
                 .push({
                    type:"switch",
                    destination: 14,
                    theme: $context.intent,
                    attributes: {                         // пречат поля
                        "Имя": "Петр",
                        "Фамилия": "Иванов",
                        "Должность:": "Менеджер"
                        
                    }                    
            });
        state: пожар
            intent!: /IT поддержка/загорелся компьютер
            a: Ищите огнетушитель! Оператор сейчас подключится и даст дальнейшие указания.
            a: Оператор подключается...
            script:
                $response.replies = $response.replies || [];
                $response.replies
                     .push({
                        type:"switch",
                        destination: 14,
                        theme: $context.intent,
                        attributes: {                         // пречат поля
                            "Имя": "Петр",
                            "Фамилия": "Иванов",
                            "Должность:": "Менеджер"
                            
                        }
                }); 
    
    state: NoMatch
        event!: noMatch
        a: Переводим на оператора...
        script:
            $response.replies = $response.replies || [];
            $response.replies
                 .push({
                    type:"switch"
            });
            
            
    state: LivechatFinished
        event!: livechatFinished
        a: Оператор завершил диалог. Переводим обратно на бота...
        go!: /NPS
    
    state: testNPS
        q!: nps
        go!: /NPS
        
    state: NPS
        a: Пожалуйста, оцените качество обслуживания:
        script:
            $analytics.setSessionLabel("Нет оценки");
            $analytics.setSessionResult("Нет оценки");
        buttons:
            "👍" -> ./good
            "👎" -> ./bad
        
        state: good
            a: Рады, что смогли вам помочь!
            script:
                $analytics.setSessionLabel("Запрос удовлетворен");
                $analytics.setSessionResult("Положительно");
            
        state: bad
            a: Спасибо за отзыв! Будем стараться улучшить качество обслуживания!
            script:
                $analytics.setSessionLabel("Провал");
                $analytics.setSessionResult("Отрицательно");
            

    state: Match
        event!: match
        a: {{$context.intent.answer}}