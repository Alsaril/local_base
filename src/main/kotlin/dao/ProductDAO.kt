package dao

import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import origin.Product

class ProductDAO(connectionSource: ConnectionSource) {
    private val dao = DaoManager.createDao(connectionSource, Product::class.java)

    init {
        TableUtils.createTableIfNotExists(connectionSource, Product::class.java)
    }

    fun saveList(products: List<Product>) = products.forEach { dao.createOrUpdate(it) }

    fun loadList() = dao.queryForAll()

    fun fromCategory(category: Int) = dao.queryForEq("category", category)

    fun fromSubcategory(subcategory: Int) = dao.queryForEq("sub_category", subcategory)

    fun fromName(name: String) = dao.queryBuilder().where().like("name", name).query()

    fun fromBarcode(barcode: String) = dao.queryBuilder().where().like("barcode", barcode).query()
}