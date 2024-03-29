package com.tregz.mvvm.main.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.tregz.mvvm.R
import com.tregz.mvvm.arch.user.UserShared
import com.tregz.mvvm.base.BaseFragment
import com.tregz.mvvm.core.date.DateUtil
import com.tregz.mvvm.data.user.UserModel
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseFragment() {

    private var user: UserModel? = null
    private var preferences: UserShared? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.let { preferences = UserShared(it) }
        if (savedInstanceState != null) {
            savedInstanceState.getParcelable<UserModel>(UserModel.TAG)?.let { user = it }
            user?.email?.let { Log.d(TAG, "User email: $it")}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, State.VIEW_INFLATING.name)
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences?.email?.let { email_editor.setText(it) }
        email_editor.setOnFocusChangeListener { editor, _ ->
            with((editor as EditText).text.toString()) {
                if (isNotEmpty()) {
                    if (UserUtil.isEmailValid(this)) {
                        user?.email = this
                        if (preferences != null) preferences?.email = this
                    } else editor.error = "Email is not valid"
                }
            }
        }
        first_name_editor.setOnFocusChangeListener { editor, _ ->
            with((editor as EditText).text.toString()) {
                if (isNotEmpty()) {
                    if (UserUtil.isPersonNameValid(this)) {
                        user?.firstName = this
                    } else editor.error = "First name is not valid"
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        listener.onFragmentStart("Auth")
    }

    companion object {
        init {
            TAG = AuthFragment::class.java.simpleName
        }

        fun getTAG() : String {
            return TAG
        }
    }
}
