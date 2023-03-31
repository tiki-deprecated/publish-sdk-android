import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.ui.elements.TikiSdkButton

@Composable
fun Terms(onAccept: () -> Unit, terms: String) {
    val context = LocalContext.current
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(listOf(terms)) { term ->
            Text(
                text = term,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 40.dp),
                textAlign = TextAlign.Justify
            )
        }
    }
    Divider(
        color = TikiSdk.theme(context).accentColor,
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 30.dp)
    )
    TikiSdkButton(
        text = "I agree",
        onTap = onAccept,
        backgroundColor = TikiSdk.theme(context).accentColor,
    )
}
