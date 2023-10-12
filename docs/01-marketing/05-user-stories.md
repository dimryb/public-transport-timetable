# Пользовательские истории

## 1. Добавление расписания маршрута
   В качестве администратора системы,
   Я хочу создать маршрут с расписанием.

Сценарий: Создание расписания маршрута.
Дано: я нахожусь на странице создания и редактирования маршрута с расписанием.

1. Сценарий: Создаем множество остановок, необходимых для создания маршрутов. \
   **Дано:** я нахожусь на странице создания и редактирования маршрута. \
   **Когда** я нажимаю добавить остановку, \
   **тогда** появляется пункт <новая остановка>, и активируется форма ввода остановки\
   а кнопка "Сохранить" неактивна, потому что не все обязательные поля заполнены \
   после заполнение всех параметров остановки \
   **и** я могу сохранить остановку, нажав на кнопку "Сохранить".
2. Сценарий: Создаем маршрут. \
   **Дано:** я нахожусь на странице создания и редактирования маршрута. \
   **Когда** я нажимаю добавить маршрут, \
   **тогда** могу ввести параметры нового маршрута.\
   После заполнение всех параметров маршрута: номер маршрута его направление \
   **и** я могу добавлять новые остановки в новый маршрут.
3. Сценарий: Добавляем остановки в маршрут из множества созданных остановок. \
   **Дано:** я нахожусь на странице создания и редактирования маршрута. \
   **Когда** я выделяю нужную остановку и нажимаю добавить остановку в маршрут, \
   **тогда** остановка добавляется в маршрут\ 
   После заполнение всех параметров маршрута: список остановок, номер маршрута его направление \
   **и** я могу сохранить маршрут, нажав на кнопку "Сохранить".
4. Сценарий: Добавляем рейсы маршрута. \
   **Дано:** я нахожусь на странице создания и редактирования маршрута. \
   **Когда** я выделяю нужный маршрут и нажимаю добавить рейс для маршрута, \
   **тогда** рейс добавляется в маршрут\
   После заполнение всех параметров рейса: время отправления и время прибытия \
   **и** я могу сохранить рейс, нажав на кнопку "Сохранить".





