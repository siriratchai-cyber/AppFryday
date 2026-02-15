import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class AdAdapter(private val list: List<Int>) :
    RecyclerView.Adapter<AdAdapter.AdViewHolder>() {

    class AdViewHolder(val image: ImageView) :
        RecyclerView.ViewHolder(image)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdViewHolder {
        val image = ImageView(parent.context)
        image.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        return AdViewHolder(image)
    }

    override fun onBindViewHolder(holder: AdViewHolder, position: Int) {
        holder.image.setImageResource(list[position])
    }

    override fun getItemCount() = list.size
}
