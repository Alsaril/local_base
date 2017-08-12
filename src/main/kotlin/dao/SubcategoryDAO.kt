package dao

import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import origin.Subcategory

class SubcategoryDAO(connectionSource: ConnectionSource) {
    private var dao = DaoManager.createDao(connectionSource, Subcategory::class.java)

    init {
        TableUtils.createTableIfNotExists(connectionSource, Subcategory::class.java)
    }

    fun saveList(subcategories: List<Subcategory>) {
        subcategories.forEach { dao.createOrUpdate(it) }
    }

    fun loadList()= dao.queryForAll()
}