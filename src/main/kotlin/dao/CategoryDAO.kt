package dao

import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import origin.Category

class CategoryDAO(connectionSource: ConnectionSource) {
    private var dao = DaoManager.createDao(connectionSource, Category::class.java)

    init {
        TableUtils.createTableIfNotExists(connectionSource, Category::class.java)
    }

    fun saveList(categories: List<Category>) {
        categories.forEach { dao.createOrUpdate(it) }
    }

    fun loadList() = dao.queryForAll()
}