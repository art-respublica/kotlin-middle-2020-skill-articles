package ru.skillbranch.skillarticles.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.skillbranch.skillarticles.data.AppSettings
import ru.skillbranch.skillarticles.data.ArticleData
import ru.skillbranch.skillarticles.data.ArticlePersonalInfo
import ru.skillbranch.skillarticles.data.repositories.ArticleRepository
import ru.skillbranch.skillarticles.extensions.data.toAppSettings
import ru.skillbranch.skillarticles.extensions.data.toArticlePersonalInfo
import ru.skillbranch.skillarticles.extensions.format

class ArticleViewModel(private val articleId: String) : BaseViewModel<ArticleState>(ArticleState()), IArticleViewModel {

    private val repository = ArticleRepository

    private val query = mutableLiveData("")

    init {
        // subscribe on mutable data
        subscribeOnDataSource(getArticleData()) { article, state ->
            article ?: return@subscribeOnDataSource null
            state.copy(
                shareLink = article.shareLink,
                title = article.title,
                category = article.category,
                categoryIcon = article.categoryIcon,
                date = article.date.format(),
                author = article.author,
                poster = article.poster
            )
        }

        subscribeOnDataSource(getArticleContent()) { content, state ->
            content ?: return@subscribeOnDataSource null
            state.copy(
                isLoadingContent = false,
                content = content
            )
        }

        subscribeOnDataSource(getArticlePersonalInfo()) { info, state ->
            info ?: return@subscribeOnDataSource null
            state.copy(
                isBookmark = info.isBookmark,
                isLike = info.isLike
            )
        }

        subscribeOnDataSource(repository.getAppSettings()) { settings, state ->
            state.copy(
                isDarkMode = settings.isDarkMode,
                isBigText = settings.isBigText
            )
        }
    }

    override fun getArticleContent(): LiveData<List<Any>?> {
        return repository.loadArticleContent(articleId)
    }

    override fun getArticleData(): LiveData<ArticleData?> {
        return repository.getArticle(articleId)
    }

    override fun getArticlePersonalInfo(): LiveData<ArticlePersonalInfo?> {
        return repository.loadArticlePersonalInfo(articleId)
    }

    override fun handleNightMode() {
        val settings: AppSettings = currentState.toAppSettings()
        repository.updateSettings(settings.copy(
            isDarkMode = !settings.isDarkMode
        ))
    }

    override fun handleUpText() {
        repository.updateSettings(currentState.toAppSettings().copy(
            isBigText = true
        ))
    }

    override fun handleDownText() {
        repository.updateSettings(currentState.toAppSettings().copy(
            isBigText = false
        ))
    }

    override fun handleBookmark() {
        val toggleBookmark: () -> Unit = {
            val info = currentState.toArticlePersonalInfo()
            repository.updateArticlePersonalInfo(info.copy( isBookmark = !info.isBookmark ))
        }

        toggleBookmark()

        val msg = if (currentState.isBookmark) { Notify.TextMessage("Add to bookmarks") }
        else { Notify.TextMessage("Remove from bookmarks") }
        notify(msg)
    }

    override fun handleLike() {
        val toggleLike: () -> Unit = {
            val info: ArticlePersonalInfo = currentState.toArticlePersonalInfo()
            repository.updateArticlePersonalInfo(info.copy( isLike = !info.isLike ))
        }

        toggleLike()

        val msg = if(currentState.isLike) Notify.TextMessage("Mark is liked")
        else {
            Notify.ActionMessage(
                "Don`t like it anymore",   // message
                "No, still like it", // action label on snackbar
                toggleLike)                     // handler function, if press "No, still like it" on snackbar, then toggle again
        }

        notify(msg)
    }

    override fun handleShare() {
        val msg = "Share is not implemented"
        notify(Notify.ErrorMessage(msg, "OK", null))
    }

    override fun handleToggleMenu() {
        updateState { it.copy(isShowMenu = !it.isShowMenu) }

    }

    override fun handleSearchMode(isSearch: Boolean) {
        TODO("Not yet implemented")
    }

    override fun handleSearch(query: String?) {
        TODO("Not yet implemented")
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }

    fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
        val data = MutableLiveData<T>()

        if (defaultValue != null) {
            data.value = defaultValue
        }

        return data
    }
}

data class ArticleState (
    val isAuth: Boolean = false,  // пользователь авторизован
    val isLoadingContent: Boolean = true,  // контент загружается
    val isLoadingReviews: Boolean = true,  // отзывы загружаются
    val isLike: Boolean = false,   // отмечено как like
    val isBookmark: Boolean = false, // в закладках
    val isShowMenu: Boolean = false, // отображается меню
    val isBigText: Boolean = false, // шрифт увеличен
    val isDarkMode: Boolean = false, // темный режим
    val isSearch: Boolean = false, // режим поиска
    val searchQuery: String? = null, // поисковый запрос
    val searchResults: List<Pair<Int, Int>> = emptyList(), // результаты поиска (стартовая и конечная позиция)
    val searchPosition: Int = 0,  // текущая позиция найденного результата
    val shareLink: String? = null, // ссылка Share
    val title: String? = null, // заголовок статьи
    val category: String? = null, // категория
    val categoryIcon: Any? = null, // иконка категории
    val date: String? = null, // дата публикации
    val author: Any? = null, // автор статьи
    val poster: String? = null, // обложка статьи
    val content: List<Any> = emptyList(), // контент
    val reviews: List<Any> = emptyList()  // комментарии
)