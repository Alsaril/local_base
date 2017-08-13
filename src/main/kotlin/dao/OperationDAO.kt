package dao

import LocalBase
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import origin.Operation


class OperationDAO(private val connectionSource: ConnectionSource) {
    private var dao = DaoManager.createDao(connectionSource, Operation::class.java)

    init {
        TableUtils.createTableIfNotExists(connectionSource, Operation::class.java)
    }

    fun save(operation: Operation) {
        dao.create(operation)
        operation.positions.forEach {
            it.setOperation(operation)
            LocalBase.positionDao.save(it)
        }
    }

    fun load() = dao.queryForAll().map { it.setPositions(LocalBase.positionDao.getFromOperation(it)) }

    fun clear() {
        LocalBase.positionDao.clear()
        TableUtils.clearTable(connectionSource, Operation::class.java)
    }
}