import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.j256.ormlite.support.ConnectionSource
import dao.CategoryDAO
import dao.ProductDAO
import dao.SubcategoryDAO
import origin.ProductsData


object LocalBase : ILocalBase {

    private val DATABASE_URL = "jdbc:sqlite:main.db"
    private val categoryDao: CategoryDAO
    private val subcategoryDao: SubcategoryDAO
    private val productDao: ProductDAO

    init {
        val connectionSource: ConnectionSource = JdbcConnectionSource(DATABASE_URL);
        categoryDao = CategoryDAO(connectionSource)
        subcategoryDao = SubcategoryDAO(connectionSource)
        productDao = ProductDAO(connectionSource)
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