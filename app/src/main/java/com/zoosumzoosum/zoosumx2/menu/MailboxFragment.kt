package com.zoosumzoosum.zoosumx2.menu

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zoosumzoosum.zoosumx2.*
import com.zoosumzoosum.zoosumx2.model.MailboxDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_mailbox.*
import kotlinx.android.synthetic.main.mailbox_list_item.view.*

class MailboxFragment : Fragment() {

    var fbAuth: FirebaseAuth? = null
    var fbFirestore: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mailbox, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fbAuth = FirebaseAuth.getInstance()
        fbFirestore = FirebaseFirestore.getInstance()

        recyclerview_mailbox_mailbox?.adapter = MailboxAdapter()
        recyclerview_mailbox_mailbox?.layoutManager = LinearLayoutManager(context)
        recyclerview_mailbox_mailbox?.setHasFixedSize(true) // recyclerview 크기 고정
        val spaceDecoration = VerticalSpaceItemDecoration(-70)
        recyclerview_mailbox_mailbox?.addItemDecoration(spaceDecoration)
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }

    inner class MailboxAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // MailboxDTO 클래스 ArrayList 생성
        private val mailBox: ArrayList<MailboxDTO> = arrayListOf()

        // townRanking의 문서를 불러온 뒤 MailboxDTO로 변환해서 ArrayList에 담음
        init {
            // 사용자의 지역구 뉴스를 가져옴
            var gu: String?
            fbFirestore?.collection("users")?.document(fbAuth?.uid.toString())
                ?.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                    if (documentSnapshot == null) return@addSnapshotListener
                    gu = documentSnapshot.data?.get("addressRegion").toString()

                    fbFirestore?.collection("news")?.whereEqualTo("addressCity", gu.toString())
                        ?.orderBy("issueDate", Query.Direction.DESCENDING)
                        ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                            // ArrayList 비워줌
                            mailBox.clear()

                            if (querySnapshot != null) {
                                for (snapshot in querySnapshot.documents) {
                                    val item = snapshot.toObject(MailboxDTO::class.java)
                                    if (item != null) {
                                        mailBox.add(item)
                                    }
                                }
                            }
                            notifyDataSetChanged()
                        }
                }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.mailbox_list_item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val viewHolder = (holder as ViewHolder).itemView

            viewHolder.textview_header_mailbox_list_item?.text =
                mailBox[position].title.toString().replace("bb", "\n")
            viewHolder.textview_summary_mailbox_list_item?.text =
                mailBox[position].summary.toString().replace("bb", "\n")
            viewHolder.textview_timestamp_mailbox_list_item.text =
                mailBox[position].issueDate.toString()
            viewHolder.textview_guoffice_mailbox_list_item?.text =
                mailBox[position].addressCity.toString()

            // recyclerview click listener -> 기사 상세화면
            viewHolder.setOnClickListener {
                val intent = Intent(context, NewsActivity::class.java)
                intent.putExtra("header", mailBox[position].title.toString())
                intent.putExtra("date", mailBox[position].issueDate.toString())
                intent.putExtra("guOffice", mailBox[position].addressCity.toString())
                intent.putExtra("content", mailBox[position].contents.toString())

                // new 제거
                viewHolder.textview_new_mailbox_list_item.text = null
                viewHolder.textview_new_mailbox_list_item.setBackgroundResource(R.color.transparent)

                startActivity(intent)
            }
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return mailBox.size
        }

    }
}
