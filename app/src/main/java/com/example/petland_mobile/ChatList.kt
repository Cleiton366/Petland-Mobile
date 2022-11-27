package com.example.petland_mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petland_mobile.adapters.ChatListAdapter
import com.example.petland_mobile.models.User
import com.facebook.drawee.view.SimpleDraweeView

class ChatList : AppCompatActivity() {

    private lateinit var chatlist : MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

        fetchChatlist()

        var recyclerview = findViewById<RecyclerView>(R.id.recycler_view_chatList_Card)
        var adapter = ChatListAdapter(chatlist)

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

    }

    private fun fetchChatlist () {
        mockChatList()
    }

    private fun mockChatList() {
        var newChatList : MutableList<User> = ArrayList()
        val names = arrayOf<String> ("Lily Sullivan", "Shannon Stanley",
            "Kristen Ortiz", "Aidyn Mccray", "Molly Wilkerson", "Vihaan Hess", "Korbin Conrad",
            "Gabriel Lane", "Francisco Castro", "Ariel Parks", "Eve Wilkins", "Paisley Cummings")
        val avatarUrl = arrayOf<String> ("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.FBGLi77e5GKIhHM2p_AAwQHaE8%26pid%3DApi&f=1&ipt=24cf320da9f5cab0fc713ea7192739af2ae3407bb2f7e80180ee5e4787d85676&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.6ZbkeqLKN1AD2tk9ancZ5wHaHW%26pid%3DApi&f=1&ipt=ba2f8c77345ac9e2511e2e8c62290202b47d8ce4f9a37f00470eb9f05c6a624a&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.hEj_DJ2TeEyxMn6_cdnnYwHaE8%26pid%3DApi&f=1&ipt=db9631200604f61e6227f52abfb25d7c84e4584229003bd7f2c6b53f9fbd9dcb&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2F736x%2F1e%2F26%2F87%2F1e26874caab099fc978107fb8a64c3f1--this-old-man-mughal-empire.jpg&f=1&nofb=1&ipt=ac64040635c8da79210496514103f4d1b384fbad18aaf04ecebd0b227331e645&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse4.mm.bing.net%2Fth%3Fid%3DOIP.EAzMf4436BdR-LVn7u_TOAHaJ4%26pid%3DApi&f=1&ipt=5e12bd8124b69e816c6de70d9e23a9770f5b51290d22ede901ded24665ab6c00&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%3Fid%3DOIP.uR3KgRq-sJayRblSz7i40QHaLF%26pid%3DApi&f=1&ipt=678468db931af65a0e2ab43e693afcf08b1cd2d27e8e243d1ddba2e81f58e3c5&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%3Fid%3DOIP.oNXrZdRt4aVDO3WHQY7JAAHaLH%26pid%3DApi&f=1&ipt=dd2ccd0a14b4603f2988e592acb35a3903b3ec157d7df90c1f7bb3201315346c&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fimg.izismile.com%2Fimg%2Fimg3%2F20100428%2F640%2Fshe_makes_random_640_03.jpg&f=1&nofb=1&ipt=d65eb2b73c2ac1c84dd21c04cb881b748d86cca354ee44e767df21773f0b01ba&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fincidentalportraits.files.wordpress.com%2F2020%2F05%2Fimg_6266.jpg%3Fstrip%3Dinfo%26w%3D1200&f=1&nofb=1&ipt=e699e03d26132485e19a4b7c2e8df475997072c587ca9d7453fcf3d8ae6e4a8a&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fimg.izismile.com%2Fimg%2Fimg2%2F20090811%2Fugly_people_23.jpg&f=1&nofb=1&ipt=bc097af6d0bb9989b3deff981024d73667c6c6c4f5d142dd659ef1e482870a9f&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Flive.staticflickr.com%2F5252%2F5403292396_0804de9bcf_b.jpg&f=1&nofb=1&ipt=1276a53126e7196c17a0779e969f08d884341c60916f0cb84fa613fdf2652311&ipo=images",
            "https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fi.dailymail.co.uk%2Fi%2Fpix%2F2013%2F08%2F29%2Farticle-2405475-1B8389EE000005DC-718_634x550.jpg&f=1&nofb=1&ipt=3907359c853a5dbb27c9868af082cfb0e55b0e86b46c616f12283e3445312664&ipo=images")

        for (i in 0..11) {
            val user = User(
                i.toString(),
                names[i],
                "",
                avatarUrl[i]
            )
            newChatList.add(i, user)
        }
        chatlist = newChatList
    }

}