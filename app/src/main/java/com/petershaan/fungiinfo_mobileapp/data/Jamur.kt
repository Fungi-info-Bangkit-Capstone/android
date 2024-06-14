package com.petershaan.fungiinfo_mobileapp.data


data class Jamur(
    val nama: String,
    val deskripsi: String,
    val ciri_ciri: List<String>,
    val manfaat_bahaya: Any
)

val daftarJamur = listOf(
    Jamur(
        "amanita",
        "Genus Amanita terdiri dari 600 spesies agarics, termasuk beberapa jamur paling beracun yang diketahui di seluruh dunia, serta beberapa spesies yang dapat dimakan . Genus ini bertanggung jawab atas sekitar 95% kematian akibat keracunan jamur, dengan death cap (Amanita phalloides) terhitung sekitar 50% nya . Racun paling kuat yang ada dalam jamur ini adalah α-Amanitin.\n\nMeskipun beberapa spesies Amanita beracun, ada juga beberapa spesies yang bisa dimakan. Namun, para ahli mikologi sangat tidak menyarankan pemetik jamur selain ahli untuk memilih apa pun dari genus ini untuk dikonsumsi manusia.",
        listOf("Tudung: Biasanya berwarna cerah, seperti merah, oranye, kuning, atau putih. Banyak spesies memiliki bintik-bintik atau tambalan pada tudungnya.",
            "Insang: Biasanya putih atau krem.",
            "Batang: Biasanya putih atau pucat, dan mungkin memiliki cincin di sekitar bagian atas atau sisa membran di pangkalnya.",
            "Volva: Struktur seperti kantung di dasar batang yang merupakan sisa-sisa membran universal yang menyelimuti seluruh jamur saat masih sangat muda. Beberapa spesies mungkin memiliki volva yang lebih jelas daripada yang lain."
        ),
        "Genus ini mengandung racun mematikan, seperti α-Amanitin, yang dapat menyebabkan kerusakan hati, gagal hati, dan bahkan kematian. Gejala keracunan biasanya muncul dalam 6-24 jam dan bisa fatal."
    ),

    Jamur(
        "laetiporus_sulphureus",
        "Spesies jamur ini merupakan spesies Laetiporus Sulphureus. Laetiporus Sulphureus, yang dikenal dengan berbagai nama seperti Jamur Ayam Hutan, Jamur Belerang Polypore, atau Kepiting Hutan Belerang, adalah jamur unik yang menarik perhatian banyak orang. Jamur ini tidak hanya memiliki penampilan yang khas, tetapi juga memiliki rasa yang lezat dan potensi manfaat kesehatan.",
        listOf(
            "Topi: Berwarna kuning cerah hingga oranye, berbentuk kipas atau tidak beraturan, dan berukuran 5 hingga 40 cm. Permukaannya berlapis-lapis dan bergelombang, menyerupai daging ayam.",
            "Insang: Berwarna putih atau krem, tebal, dan berdekatan.",
            "Batang: Berwarna putih atau kuning, kokoh, dan sering kali bercabang.",
            "Habitat: Tumbuh di hutan gugur dan hutan konifer, biasanya pada batang pohon mati atau yang sedang membusuk."
        ),
        listOf(
            "Meningkatkan Sistem Kekebalan Tubuh:\nLaetiporus Sulphureus mengandung beta-glukan, sejenis serat yang dapat membantu menstimulasi sistem kekebalan tubuh. Beta-glukan bekerja dengan mengaktifkan sel-sel kekebalan tubuh tertentu, seperti makrofag dan sel pembunuh alami, untuk melawan infeksi dan penyakit.",
            "Sifat Antioksidan:\nJamur ini kaya akan antioksidan, seperti vitamin C dan E. Antioksidan membantu melindungi tubuh dari kerusakan akibat radikal bebas. Radikal bebas dapat menyebabkan stres oksidatif, yang terkait dengan berbagai penyakit kronis seperti kanker, penyakit jantung, dan Alzheimer.",
            "Meningkatkan Kesehatan Pencernaan:\nLaetiporus Sulphureus mengandung prebiotik, serat larut yang membantu meningkatkan kesehatan pencernaan dengan mendorong pertumbuhan bakteri menguntungkan dalam usus. Bakteri ini membantu pencernaan makanan, penyerapan nutrisi, dan meningkatkan sistem kekebalan tubuh."
        )
    ),

    Jamur(
        "lactarius_deliciosus",
        "Spesies jamur ini merupakan spesies Lactarius Deliciosus. Lactarius Deliciosus, yang lebih dikenal sebagai Jamur Susu Oranye atau Jamur Pinus Merah, adalah salah satu jamur paling terkenal dalam genus Lactarius yang besar. Jamur ini berasal dari Eropa, tetapi telah secara tidak sengaja diperkenalkan ke negara lain bersama dengan pohon pinus, simbionnya. Lactarius Deliciosus dikenal karena rasanya yang lezat dan aromanya yang harum. Jamur ini biasanya dimasak dengan cara ditumis, digoreng, atau dipanggang. Lactarius Deliciosus juga dapat dikeringkan dan digunakan sebagai bumbu.",
        listOf(
            "Topi: Berwarna oranye seperti wortel, berbentuk cembung hingga seperti vas, dan berdiameter 4 hingga 14 cm. Permukaannya lengket dan basah saat basah, dan sering kali memiliki garis-garis oranye yang lebih gelap dalam lingkaran konsentris.",
            "Insang: Berwarna oranye cerah, tebal, dan berdekatan. Saat dipotong, jamur ini mengeluarkan getah susu berwarna oranye yang khas.",
            "Batang: Berwarna oranye, kokoh, dan sering kali berongga.",
            "Habitat: Tumbuh di hutan pinus, baik di tanah maupun di antara serasah."
        ),
        listOf(
            "Kaya Antioksidan:\nJamur ini kaya akan antioksidan, seperti vitamin C dan E, yang membantu melindungi tubuh dari kerusakan akibat radikal bebas. Radikal bebas dapat menyebabkan stres oksidatif, yang terkait dengan berbagai penyakit kronis seperti kanker, penyakit jantung, dan Alzheimer.",
            "Sifat Anti-inflamasi:\nLactarius Deliciosus mengandung senyawa anti-inflamasi seperti ergosterol dan beta-glukan. Senyawa ini dapat membantu mengurangi peradangan kronis, yang merupakan faktor risiko banyak penyakit. Peradangan kronis dapat menyebabkan penyakit jantung, diabetes, radang sendi, dan penyakit autoimun.",
            "Meningkatkan Kesehatan Pencernaan:\nJamur ini mengandung prebiotik, serat larut yang membantu meningkatkan kesehatan pencernaan dengan mendorong pertumbuhan bakteri menguntungkan dalam usus. Bakteri ini membantu pencernaan makanan, penyerapan nutrisi, dan meningkatkan sistem kekebalan tubuh."
        )
    ),

    Jamur(
        "flammulina_velutipes",
        "Spesies jamur ini merupakan spesies Flammulina Velutipes. Flammulina velutipes, yang juga dikenal sebagai jamur enoki, enokitake, atau kaki beludru, adalah jamur basidiomycete yang dapat dimakan dalam keluarga Physalacriaceae. Spesies ini dapat ditemukan di Eropa dan Amerika Utara. Sebelumnya, Flammulina velutipes dianggap sebagai spesies yang sama dengan Flammulina filiformis dari Asia yang dibudidayakan untuk makanan, tetapi analisis DNA menunjukkan bahwa keduanya berbeda.",
        listOf(
            "Topi: Berwarna coklat muda hingga coklat tua, terkadang dengan semburat merah atau kuning. Bentuk topi awalnya cembung, kemudian menjadi lebih melebar seiring bertambahnya usia. Permukaan topi bisa halus atau sedikit berkerut.",
            "Batang: Kokoh dan berwarna putih sampai krem. Permukaan batang memiliki pola seperti jala di bagian atas, yang merupakan ciri khas dari jamur Flammulina velutipes.",
            "Bunga: Berwarna putih kekuningan hingga krem saat muda, kemudian berubah menjadi kuning kehijauan seiring bertambahnya usia. Bentuk pori-pori halus dan bulat.",
            "Daging buah: Berwarna putih, padat, dan tidak berubah warna saat dipotong. Memiliki aroma dan rasa yang harum dan nutty."
        ),
        listOf(
            "Meningkatkan Sistem Kekebalan Tubuh:\nJamur enoki kaya akan beta-glukan, sejenis serat larut yang dapat membantu meningkatkan sistem kekebalan tubuh dengan merangsang sel-sel darah putih untuk melawan infeksi. Kandungan vitamin C dalam jamur enoki juga berperan dalam meningkatkan fungsi kekebalan tubuh.",
            "Menjaga Kesehatan Pencernaan:\nSerat larut dalam jamur enoki membantu melancarkan pencernaan, mencegah sembelit, dan menjaga kesehatan usus. Jamur ini juga mengandung prebiotik, yang merupakan makanan bagi bakteri baik dalam usus, sehingga dapat membantu menjaga keseimbangan mikrobioma usus.",
            "Menurunkan Kolesterol:\nBeberapa penelitian menunjukkan bahwa konsumsi jamur enoki secara teratur dapat membantu menurunkan kadar kolesterol jahat (LDL) dalam darah. Hal ini kemungkinan karena kandungan serat dan senyawa antioksidan dalam jamur enoki.",
            "Mencegah Kanker:\nPenelitian pada hewan menunjukkan bahwa ekstrak jamur enoki memiliki efek anti-kanker. Kandungan antioksidan dalam jamur enoki, seperti vitamin C dan selenium, juga dapat membantu melindungi tubuh dari kerusakan akibat radikal bebas yang dapat menyebabkan kanker.",
            "Menjaga Kesehatan Jantung:\nJamur enoki mengandung kalium, mineral penting yang membantu mengatur tekanan darah. Kandungan serat dan antioksidan dalam jamur enoki juga dapat membantu menjaga kesehatan jantung.",
            "Meningkatkan Fungsi Otak:\nBeberapa penelitian menunjukkan bahwa konsumsi jamur enoki secara teratur dapat membantu meningkatkan fungsi otak dan memori. Hal ini kemungkinan karena kandungan vitamin B dan mineral dalam jamur enoki.",
            "Menjaga Kesehatan Tulang:\nJamur enoki mengandung vitamin D, yang penting untuk kesehatan tulang. Kandungan kalsium dan magnesium dalam jamur enoki juga membantu memperkuat tulang.",
            "Membantu Menurunkan Berat Badan:\nJamur enoki rendah kalori dan tinggi serat, sehingga dapat membantu Anda merasa kenyang lebih lama dan mengontrol nafsu makan. Hal ini dapat membantu Anda menurunkan berat badan atau menjaga berat badan ideal."
        )
    )
)