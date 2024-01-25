package space.rybakov.timetable.backend.repo.sql

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import space.rybakov.timetable.common.models.*

object TripTable : Table("ad") {
    val id = varchar("id", 128)
    val name = varchar("name", 128)
    val description = varchar("description", 512)
    val owner = varchar("owner", 128)
    val direction = enumeration("direction", TimetableDirection::class)
    val lock = varchar("lock", 50)

    override val primaryKey = PrimaryKey(id)

    fun from(res: InsertStatement<Number>) = TimetableTrip(
        id = TimetableTripId(res[id].toString()),
        name = res[name],
        description = res[description],
        ownerId = TimetableUserId(res[owner].toString()),
        tripType = res[direction],
        lock = TimetableTripLock(res[lock])
    )

    fun from(res: ResultRow) = TimetableTrip(
        id = TimetableTripId(res[id].toString()),
        name = res[name],
        description = res[description],
        ownerId = TimetableUserId(res[owner].toString()),
        tripType = res[direction],
        lock = TimetableTripLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, ad: TimetableTrip, randomUuid: () -> String) {
        it[id] = ad.id.takeIf { it != TimetableTripId.NONE }?.asString() ?: randomUuid()
        it[name] = ad.name
        it[description] = ad.description
        it[owner] = ad.ownerId.asString()
        it[direction] = ad.tripType
        it[lock] = ad.lock.takeIf { it != TimetableTripLock.NONE }?.asString() ?: randomUuid()
    }

}
