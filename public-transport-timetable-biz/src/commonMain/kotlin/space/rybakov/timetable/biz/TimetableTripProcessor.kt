package space.rybakov.timetable.biz

import space.rybakov.timetable.biz.general.initRepo
import space.rybakov.timetable.biz.general.operation
import space.rybakov.timetable.biz.general.prepareResult
import space.rybakov.timetable.biz.general.stubs
import space.rybakov.timetable.biz.repo.*
import space.rybakov.timetable.biz.validation.*
import space.rybakov.timetable.biz.workers.*
import space.rybakov.timetable.common.TimetableContext
import space.rybakov.timetable.common.TimetableCorSettings
import space.rybakov.timetable.common.models.TimetableCommand
import space.rybakov.timetable.common.models.TimetableState
import space.rybakov.timetable.common.models.TimetableTripId
import space.rybakov.timetable.cor.chain
import space.rybakov.timetable.cor.rootChain
import space.rybakov.timetable.cor.worker

class TimetableTripProcessor(val settings: TimetableCorSettings = TimetableCorSettings()) {
    suspend fun exec(ctx: TimetableContext) =
        BusinessChain.exec(ctx.apply { this.settings = this@TimetableTripProcessor.settings })

    companion object {
        private val BusinessChain = rootChain<TimetableContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")
            operation("Создание объявления", TimetableCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadName("Имитация ошибки валидации имени")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в tripValidating") { tripValidating = tripRequest.deepCopy() }
                    worker("Очистка id") { tripValidating.id = TimetableTripId.NONE }
                    worker("Очистка имени") { tripValidating.name = tripValidating.name.trim() }
                    worker("Очистка описания") { tripValidating.description = tripValidating.description.trim() }
                    validateNameNotEmpty("Проверка, что заголовок не пуст")
                    validateNameHasContent("Проверка символов")
                    validateDescriptionNotEmpty("Проверка, что описание не пусто")
                    validateDescriptionHasContent("Проверка символов")

                    finishTripValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание объявления в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Получить объявление", TimetableCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в tripValidating") { tripValidating = tripRequest.deepCopy() }
                    worker("Очистка id") { tripValidating.id = TimetableTripId(tripValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishTripValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение объявления из БД")
                    worker {
                        title = "Подготовка ответа для Read"
                        on { state == TimetableState.RUNNING }
                        handle { tripRepoDone = tripRepoRead }
                    }
                }
                prepareResult("Подготовка ответа")
            }
            operation("Изменить объявление", TimetableCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadName("Имитация ошибки валидации имени")
                    stubValidationBadDescription("Имитация ошибки валидации описания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в tripValidating") { tripValidating = tripRequest.deepCopy() }
                    worker("Очистка id") { tripValidating.id = TimetableTripId(tripValidating.id.asString().trim()) }
                    worker("Очистка имени") { tripValidating.name = tripValidating.name.trim() }
                    worker("Очистка описания") { tripValidating.description = tripValidating.description.trim() }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateNameNotEmpty("Проверка на непустой заголовок")
                    validateNameHasContent("Проверка на наличие содержания в заголовке")
                    validateDescriptionNotEmpty("Проверка на непустое описание")
                    validateDescriptionHasContent("Проверка на наличие содержания в описании")

                    finishTripValidation("Успешное завершение процедуры валидации")
                    chain {
                        title = "Логика сохранения"
                        repoRead("Чтение объявления из БД")
                        repoPrepareUpdate("Подготовка объекта для обновления")
                        repoUpdate("Обновление объявления в БД")
                    }
                    prepareResult("Подготовка ответа")
                }
            }
            operation("Удалить объявление", TimetableCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") {
                        tripValidating = tripRequest.deepCopy()
                    }
                    worker("Очистка id") { tripValidating.id = TimetableTripId(tripValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishTripValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение объявления из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление объявления из БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Поиск объявлений", TimetableCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adFilterValidating") { tripFilterValidating = tripFilterRequest.copy() }

                    finishTripFilterValidation("Успешное завершение процедуры валидации")
                }
                repoSearch("Поиск объявления в БД по фильтру")
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}
