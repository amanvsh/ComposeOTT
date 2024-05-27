package com.example.ott.repository


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ott.model.Content

class ListDataSource  (private val repository: ContentListRepository) : PagingSource<Int, Content>() {

    override fun getRefreshKey(state: PagingState<Int, Content>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus( 1) ?: page?.nextKey?.plus( 1)
        }
    }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {
         try {

             val page = params.key ?: 1  // Start from page 1 for initial load

             return try {
                 val contentList = repository.getContentList(page)
                 val nextKey = if (contentList.page?.nextPage == "true") page + 1 else null

                 LoadResult.Page(
                     data = contentList.page?.contentItems?.content!!,
                     prevKey = null, // No previous page for initial load
                     nextKey = nextKey
                 )

             } catch (e: Exception) {
                 LoadResult.Error(e)
             }

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}