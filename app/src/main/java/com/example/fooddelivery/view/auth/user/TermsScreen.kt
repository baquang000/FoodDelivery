package com.example.fooddelivery.view.auth.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fooddelivery.R
import com.example.fooddelivery.components.NormalTextComponents

@Composable
fun TermsScreen(
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = {
                    navController.navigateUp()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow),
                        contentDescription = stringResource(
                            id = R.string.arrow
                        ),
                        modifier = Modifier.size(width = 24.dp, height = 24.dp)
                    )
                }
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "1. Giới thiệu",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Chào mừng bạn đến với ứng dụng đặt đồ ăn. Ứng dụng này được cung cấp bởi Nguyễn Bá Quảng. Các Điều khoản sử dụng này  điều chỉnh quyền truy cập và sử dụng Ứng dụng của bạn.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "2. Chấp nhận các điều khoản",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Bằng cách truy cập hoặc sử dụng Ứng dụng, bạn đồng ý bị ràng buộc bởi các Điều khoản này. Nếu bạn không đồng ý với các Điều khoản này, bạn không được phép truy cập hoặc sử dụng Ứng dụng.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "3. Tài khoản người dùng",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "3.1 Đăng ký tài khoản",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Để sử dụng một số tính năng nhất định của Ứng dụng, bạn có thể được yêu cầu tạo một tài khoản. Khi tạo tài khoản, bạn đồng ý cung cấp cho chúng tôi thông tin chính xác và đầy đủ. Bạn có trách nhiệm duy trì tính bảo mật của thông tin đăng nhập tài khoản của bạn và đối với tất cả các hoạt động diễn ra trong tài khoản của bạn. Bạn đồng ý thông báo cho chúng tôi ngay lập tức về bất kỳ việc sử dụng trái phép nào đối với tài khoản của bạn.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "3.2 Chấm dứt tài khoản",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Chúng tôi có thể chấm dứt tài khoản của bạn bất kỳ lúc nào, có hoặc không có thông báo, vì bất kỳ lý do nào, bao gồm nhưng không giới hạn ở việc vi phạm các Điều khoản này. Bạn cũng có thể chấm dứt tài khoản của mình bất kỳ lúc nào bằng cách liên hệ với chúng tôi.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "4. Sử dụng ứng dụng",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "4.1 Sử dụng được phép",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Bạn chỉ được sử dụng Ứng dụng cho các mục đích hợp pháp và theo các Điều khoản này. Bạn đồng ý không sử dụng Ứng dụng theo bất kỳ cách nào có thể gây hư hỏng, vô hiệu hóa, quá tải hoặc làm suy yếu Ứng dụng hoặc can thiệp vào việc sử dụng Ứng dụng của bất kỳ bên nào khác.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "4.2 Sử dụng bị cấm",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Bạn đồng ý không sử dụng Ứng dụng cho bất kỳ mục đích nào sau đây:",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Tạo, truyền tải hoặc phân phối nội dung bất hợp pháp, có hại, đe dọa, lạm dụng, quấy rối, vi phạm pháp luật, phỉ báng, thô tục, khiêu dâm, bôi nhọ, xâm phạm quyền riêng tư của người khác , thù hận hoặc phân biệt chủng tộc, dân tộc hoặc đáng phản đối theo cách khác.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Mạo danh bất kỳ cá nhân hoặc tổ chức nào, hoặc khai báo sai sự thật hoặc xuyên tạc mối quan hệ của bạn với một cá nhân hoặc tổ chức.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Làm giả tiêu đề hoặc thao túng các mã định danh để che giấu nguồn gốc của bất kỳ nội dung nào được truyền qua Ứng dụng.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Để can thiệp hoặc làm gián đoạn Ứng dụng hoặc máy chủ hoặc mạng được kết nối với Ứng dụng.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Truyền hoặc đưa vào bất kỳ loại vi-rút, ngựa thành Troy, sâu hoặc phần mềm độc hại nào khác.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Cố gắng truy cập trái phép vào Ứng dụng, bất kỳ tài khoản nào liên quan hoặc bất kỳ trang web hoặc hệ thống nào khác thông qua Ứng dụng.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Thu thập hoặc lưu trữ thông tin cá nhân về người dùng khác mà không có sự đồng ý của họ.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Sử dụng Ứng dụng cho bất kỳ mục đích thương mại nào mà không có sự đồng ý trước bằng văn bản của chúng tôi.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "5. Nội dung của người dùng",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Bạn có thể gửi nội dung đến Ứng dụng, chẳng hạn như đánh giá, xếp hạng và bình luận (gọi chung là \"Nội dung của người dùng\"). Bạn giữ quyền sở hữu Nội dung của người dùng. Bằng cách gửi Nội dung của người dùng đến Ứng dụng, bạn cấp cho chúng tôi giấy phép không độc quyền, trên toàn thế giới, miễn phí bản quyền, có thể cấp phép lại để sử dụng, sao chép, sửa đổi, phân phối và hiển thị Nội dung của người dùng của bạn liên quan đến Ứng dụng và doanh nghiệp của chúng tôi. Bạn tuyên bố và bảo đảm rằng bạn có mọi quyền cần thiết để cấp cho chúng tôi giấy phép đó.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "Chúng tôi có thể xóa hoặc chỉnh sửa Nội dung của Người dùng theo quyết định riêng của chúng tôi. Chúng tôi cũng có thể theo dõi Nội dung của Người dùng để tìm các hành vi vi phạm các Điều khoản này và các luật hiện hành khác.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "6. Sở hữu trí tuệ",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Ứng dụng và tất cả nội dung và tài liệu có trong Ứng dụng, bao gồm nhưng không giới hạn ở văn bản, hình ảnh, đồ họa, logo, nhãn hiệu và nhãn hiệu dịch vụ, đều là tài sản của Công ty hoặc bên cấp phép của Công ty. Bạn không được sử dụng bất kỳ tài sản trí tuệ nào của Công ty mà không có sự đồng ý trước bằng văn bản của chúng tôi.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "7. Dịch vụ của bên thứ ba",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Ứng dụng có thể tích hợp với các dịch vụ của bên thứ ba, chẳng hạn như bộ xử lý thanh toán và dịch vụ lập bản đồ. Việc bạn sử dụng các dịch vụ của bên thứ ba này có thể phải tuân theo các điều khoản và điều kiện riêng của họ. Bạn đồng ý tuân thủ tất cả các điều khoản và điều kiện đó.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "8. Tuyên bố miễn trừ trách nhiệm",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "ỨNG DỤNG ĐƯỢC CUNG CẤP THEO NGUYÊN TRẠNG \"NGUYÊN TRẠNG\" VÀ \"THEO KHẢ NĂNG CÓ SẴN\". CHÚNG TÔI KHÔNG BẢO ĐẢM BẤT KỲ LOẠI NÀO, RÕ RÀNG HAY NGỤ Ý, BAO GỒM NHƯNG KHÔNG GIỚI HẠN Ở, CÁC BẢO ĐẢM NGỤ Ý VỀ KHẢ NĂNG THƯƠNG MẠI, SỰ PHÙ HỢP CHO MỘT MỤC ĐÍCH CỤ THỂ VÀ KHÔNG VI PHẠM. CHÚNG TÔI KHÔNG BẢO ĐẢM RẰNG ỨNG DỤNG SẼ KHÔNG BỊ GIÁN ĐOẠN HOẶC KHÔNG CÓ LỖI.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
                NormalTextComponents(
                    value = "CHÚNG TÔI KHÔNG CHỊU TRÁCH NHIỆM ĐỐI VỚI BẤT KỲ THIỆT HẠI NÀO PHÁT SINH TỪ VIỆC BẠN SỬ DỤNG ỨNG DỤNG, BAO GỒM NHƯNG KHÔNG GIỚI HẠN Ở CÁC THIỆT HẠI TRỰC TIẾP, GIÁN TIẾP, NGẪU NHIÊN, ĐẶC BIỆT, HẬU QUẢ HOẶC MẪU MỰC, NGAY CẢ KHI CHÚNG TÔI ĐÃ ĐƯỢC THÔNG BÁO VỀ KHẢ NĂNG XẢY RA NHỮNG THIỆT HẠI NHƯ VẬY.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }

        item {
            Column {
                NormalTextComponents(
                    value = "9. Giới hạn trách nhiệm",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Tổng trách nhiệm của chúng tôi đối với mọi khiếu nại phát sinh từ việc bạn sử dụng Ứng dụng sẽ không vượt quá số tiền bạn đã trả cho Ứng dụng.",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
        item {
            Column {
                NormalTextComponents(
                    value = "10. Bồi thường",
                    nomalColor = Color.Black,
                    nomalFontsize = 18.sp,
                    nomalFontWeight = FontWeight.Bold
                )
                NormalTextComponents(
                    value = "Bạn đồng ý bồi thường, bảo vệ và giữ cho người sáng tạo được vô hại",
                    nomalFontsize = 14.sp,
                    nomalColor = Color.Black,
                    nomalFontWeight = FontWeight.Normal,
                    nomalTextAlign = TextAlign.Justify,
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 4.dp)
                )
            }
        }
    }
}