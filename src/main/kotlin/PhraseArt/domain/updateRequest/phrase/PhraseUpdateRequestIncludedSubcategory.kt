package PhraseArt.domain.updateRequest.phrase

import PhraseArt.domain.category.CategoryId
import PhraseArt.domain.category.SubcategoryId

// UpdateRequestFinishDecisionServiceで以下のプロパティを有する申請を引数にもつメソッドで利用するために作成したInterface。
// 名言登録申請と名言修正申請(サブカテゴリーに関するプロパティを有するクラス)が継承するinterface。
// あまり適した使い方ではない気がするので、運用していてより良い方法が思いついたら消す。
interface PhraseUpdateRequestIncludedSubcategory {
    val requestedCategoryId: CategoryId

    var requestedSubcategoryId: SubcategoryId?

    var requestedSubcategoryName: String?

    fun changeSubcategoryId(subcategoryId: SubcategoryId)
}
