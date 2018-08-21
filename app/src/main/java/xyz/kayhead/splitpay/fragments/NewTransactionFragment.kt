package xyz.kayhead.splitpay.fragments


import android.content.Context
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.*
import xyz.kayhead.splitpay.R


/**
 * A simple [Fragment] subclass.
 * Use the [NewTransactionFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class NewTransactionFragment : Fragment(), View.OnClickListener {


    var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    /*    arguments?.let {
             = it.getString(ARG_)
             = it.getString(ARG_)
        }*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_transaction, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val openCameraButton: Button = getView().findViewById(R.id.open_camera)
        openCameraButton.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        listener?.onButtonPressed(v?.id!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    interface OnFragmentInteractionListener {
        fun onButtonPressed(id: Int)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment NewTransactionFragment.
         */
        @JvmStatic
        fun newInstance() =
                NewTransactionFragment() as Fragment
                /*NewTransactionFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_, )
                        putString(ARG_, )
                    }
                }*/
    }
}
