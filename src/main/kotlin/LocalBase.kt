import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.support.ConnectionSource
import dao.CategoryDAO
import dao.ProductDAO
import dao.SubcategoryDAO
import origin.ProductsData
import java.sql.DriverManager
import java.sql.SQLException


object LocalBase : ILocalBase {

    private val DATABASE_URL = "jdbc:sqlite:main.db"
    private lateinit var categoryDao: CategoryDAO
    private lateinit var subcategoryDao: SubcategoryDAO
    private lateinit var productDao: ProductDAO

    override fun init() {
        try {
            DriverManager.getConnection(DATABASE_URL).use { conn ->
                conn?.let {
                    val meta = it.metaData
                    println("The driver name is " + meta.driverName)
                    println("A new Database has been created.")
                }
            }

            val connectionSource: ConnectionSource = JdbcConnectionSource(DATABASE_URL);
            categoryDao = CategoryDAO(connectionSource)
            subcategoryDao = SubcategoryDAO(connectionSource)
            productDao = ProductDAO(connectionSource)
        } catch (e: SQLException) {
            println(e.message)
        }
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

}