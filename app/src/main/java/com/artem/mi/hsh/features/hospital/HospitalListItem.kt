package com.artem.mi.hsh.features.hospital

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.artem.mi.hsh.R
import com.artem.mi.hsh.features.hospital.model.HospitalUiModel
import com.artem.mi.hsh.ui.common.DevicesPreview
import com.artem.mi.hsh.ui.common.widgets.HtmlText
import com.artem.mi.hsh.ui.theme.HSHTheme

@Composable
fun HospitalListItem(
    modifier: Modifier = Modifier,
    data: HospitalUiModel
) {
    Column(modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(8.dp)) {
            HtmlText(
                modifier = modifier.fillMaxWidth(),
                html = data.label
            )
            Spacer(modifier = Modifier.height(4.dp))
            HtmlText(html = data.description)
            Spacer(modifier = Modifier.height(4.dp))
            IconTextRow(iconRes = R.drawable.ic_description_24, label = data.profile)
            Spacer(modifier = Modifier.height(4.dp))
            IconTextRow(iconRes = R.drawable.ic_location_on_24, label = data.address)
            Spacer(modifier = Modifier.height(4.dp))
            IconTextRow(iconRes = R.drawable.ic_phone_24, label = data.phoneNumber)
            Spacer(modifier = Modifier.height(4.dp))
            SideText(leftRes = R.string.hospital_screen_last_update, right = data.lastUpdateDate)
            Spacer(modifier = Modifier.height(4.dp))
            SideText(leftRes = R.string.hospital_screen_available_date, right = data.availableDate)
        }
    }
}

@Composable
private fun SideText(
    modifier: Modifier = Modifier,
    @StringRes leftRes: Int,
    right: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = leftRes))
        Spacer(modifier = Modifier.width(4.dp))
        HtmlText(html = right)
    }
}

@Composable
private fun IconTextRow(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int,
    label: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = "")
        Spacer(modifier = Modifier.width(4.dp))
        HtmlText(html = label)
    }
}

@DevicesPreview
@Composable
private fun PreviewHospitalListItem() {
    HSHTheme {
        HospitalListItem(
            data = HospitalUiModel(
                uniqueId = 0,
                label = "label",
                description = "description",
                profile = "profile",
                address = "Address",
                phoneNumber = "+48 34 364 02 59",
                lastUpdateDate = "17.11.2023 r.",
                availableDate = "20.11.2023 r."
            )
        )
    }
}