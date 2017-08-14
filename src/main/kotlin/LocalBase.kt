import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.logger.LocalLog
import com.j256.ormlite.support.ConnectionSource
import dao.*
import origin.Operation
import origin.ProductsData


object LocalBase : ILocalBase {

    private val DATABASE_URL = "jdbc:sqlite:main.db"
    val categoryDao: CategoryDAO
    val subcategoryDao: SubcategoryDAO
    val productDao: ProductDAO
    val operationDao: OperationDAO
    val positionDao: PositionDAO

    init {
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "warning")
        val connectionSource: ConnectionSource = JdbcConnectionSource(DATABASE_URL);
        categoryDao = CategoryDAO(connectionSource)
        subcategoryDao = SubcategoryDAO(connectionSource)
        productDao = ProductDAO(connectionSource)
        operationDao = OperationDAO(connectionSource)
        positionDao = PositionDAO(connectionSource)
    }

    override fun save(data: ProductsData) {
        categoryDao.saveList(data.categories)
        subcategoryDao.saveList(data.subcategories)
        productDao.saveList(data.products)
    }

    override fun getProducts() = productDao.loadList()

    override fun getCategories() = categoryDao.loadList()

    override fun getSubcategories() = subcategoryDao.loadList()

    override fun getProductsFromCategory(category: Int) = productDao.fromCategory(category)

    override fun getProductsFromSubcategory(subcategory: Int) = productDao.fromSubcategory(subcategory)

    override fun getProductsFromName(name: String) = productDao.fromName(name)

    override fun getProductsFromBarcode(barcode: String) = productDao.fromBarcode(barcode)

    override fun saveOperation(operation: Operation) = operationDao.save(operation)

    override fun getOperations() = operationDao.load()

    override fun clearOperations() {
        operationDao.clear()
        positionDao.clear()
    }
}