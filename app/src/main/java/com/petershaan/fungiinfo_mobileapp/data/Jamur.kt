package com.petershaan.fungiinfo_mobileapp.data


data class Jamur(
    val label: String,
    val nama: String,
    val deskripsi: String,
    val ciri_ciri: List<String>,
    val manfaat_bahaya: Any
)

val daftarJamur = listOf(
    Jamur(
        "amanita",
        "Amanita",
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
        "Laetiporus Sulphureus",
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
        "Lactarius Deliciosus",
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
        "Flammulina Velutipes",
        "Dikenal sebagai jamur enoki, enokitake, atau kaki beludru, adalah jamur basidiomycete yang dapat dimakan dalam keluarga Physalacriaceae. Spesies ini dapat ditemukan di Eropa dan Amerika Utara. Sebelumnya, Flammulina velutipes dianggap sebagai spesies yang sama dengan Flammulina filiformis dari Asia yang dibudidayakan untuk makanan, tetapi analisis DNA menunjukkan bahwa keduanya berbeda.\n" +
                "\n" +
                "Flammulina velutipes adalah jamur yang populer dalam masakan Asia, dan sering digunakan dalam sup, tumis, dan salad. Jamur ini memiliki rasa yang ringan dan sedikit nutty, dan teksturnya yang renyah membuatnya menjadi tambahan yang bagus untuk berbagai hidangan. Flammulina velutipes juga merupakan sumber vitamin dan mineral yang baik, termasuk vitamin B1, B2, dan C, kalium, dan fosfor.\n",
        listOf(
            "Topi: Berwarna coklat muda hingga coklat tua, terkadang dengan semburat merah atau kuning. Bentuk topi awalnya cembung, kemudian menjadi lebih melebar seiring bertambahnya usia. Permukaan topi bisa halus atau sedikit berkerut.",
            "Batang: Kokoh dan berwarna putih sampai krem. Permukaan batang memiliki pola seperti jala di bagian atas, yang merupakan ciri khas dari jamur Flammulina velutipes.",
            "Bunga: Berwarna putih kekuningan hingga krem saat muda, kemudian berubah menjadi kuning kehijauan seiring bertambahnya usia. Bentuk pori-pori halus dan bulat.",
            "Daging buah: Berwarna putih, padat, dan tidak berubah warna saat dipotong. Memiliki aroma dan rasa yang harum dan nutty."
        ),
        listOf(
            "Kesehatan Pencernaan:\nSerat larut dalam jamur enoki membantu menjaga kesehatan pencernaan dengan melancarkan proses pencernaan, mencegah sembelit, dan menjaga keseimbangan mikrobioma usus.",
            "Kesehatan Jantung dan Otak:\nPenelitian menunjukkan bahwa konsumsi jamur enoki secara teratur dapat menurunkan kadar kolesterol jahat (LDL) dalam darah dan memiliki efek anti-kanker, yang didukung oleh kandungan serat dan antioksidan seperti vitamin C dan selenium. Selain itu, jamur enoki juga dapat menjaga kesehatan jantung dengan kandungan kaliumnya serta membantu meningkatkan fungsi otak dan memori melalui kandungan vitamin B dan mineral.",
            "Kesehatan Tulang:\nKandungan vitamin D, kalsium, dan magnesium dalam jamur enoki juga membantu menjaga kesehatan tulang."
        )
    ),

    Jamur(
        "boletus_reticulatus",
        "Boletus Reticulatus",
        "Spesies jamur ini merupakan spesies Reticulatus. Boletus reticulatus, sering disebut sebagai summer cep atau netted bolete, adalah jamur basidiomycete besar yang dapat dimakan . Jamur ini berasal dari Eropa dan hidup berdampingan secara simbiosis dengan pohon-pohon gugur seperti oak, beech, chestnut manis, hazel, hornbeam, dan lime. Boletus reticulatus menghasilkan tubuh buah pada musim panas dan dianggap sama lezatnya dengan kerabat dekatnya, Boletus edulis atau cep.",
        listOf(
            "Tudung: Diameter hingga 20 cm, berbentuk setengah bola, cembung, atau cembung datar lalu permukaan tudung biasanya kering, tetapi bisa sedikit lengket saat cuaca basah atau jamur semakin tua. Warnanya biasanya coklat muda hingga coklat tua, terkadang dengan semburat keabu-abuan atau keputihan. Uniknya, tudung Boletus reticulatus tidak berubah menjadi biru saat受伤 (shòushang) atau terluka.",
            "Batang: Bentuknya seperti gada, silindris, atau hampir sewarna dengan tudung lalu seluruh permukaan batang ditutupi dengan pola seperti jala yang halus (reticulated). Inilah ciri khas yang membedakannya dari jamur sejenis lainnya.",
            "Daging Buah: Berwarna putih, meskipun mungkin memiliki bintik-bintik kecoklatan di dekat pangkal batang dan daging buah tidak berubah warna saat terkena udara.",
            "Tabung: Awalnya berwarna putih, kemudian menjadi krem, kuning pucat, atau kuning dengan sedikit warna zaitun dan Tabung tidak berubah warna saat ditekan",
            "Pori-pori: Warnanya sama dengan tabung dan tidak berubah warna saat ditekan",
            "Bau dan Rasa: Tidak memiliki bau atau rasa yang menonjol."
        ),
        listOf(
            "Kaya Nutrisi:\nBoletus reticulatus adalah sumber vitamin dan mineral yang baik, termasuk vitamin B1, B2, dan C, kalium, fosfor, dan magnesium. lalu Jamur ini juga mengandung serat makanan, yang penting untuk pencernaan yang sehat. Kandungan proteinnya yang tinggi menjadikannya pilihan yang baik bagi vegetarian dan vegan.",
            "Antioksidan:\nBoletus reticulatus kaya akan antioksidan, yang dapat membantu melindungi tubuh dari kerusakan akibat radikal bebas dan radikal bebas dapat menyebabkan stres oksidatif, yang terkait dengan berbagai penyakit kronis seperti kanker, penyakit jantung, dan penyakit Alzheimer.",
            "Sifat Anti-inflamasi:\nBoletus reticulatus memiliki sifat anti-inflamasi yang dapat membantu mengurangi peradangan dalam tubuh. Peradangan kronis dapat menyebabkan berbagai penyakit seperti penyakit jantung, kanker, dan arthritis."
        )
    ),
    Jamur(
        "boletus_edulis",
        "Boletus Edulis",
        "Spesies jamur ini merupakan spesies Boletus Edulis. Boletus edulis, yang juga dikenal dengan nama cep, porcini, atau penny bun bolete, adalah jamur basidiomycete yang dapat dimakan dan banyak dicari . Jamur ini berasal dari Eropa, Asia, dan Amerika Utara, tetapi juga telah diintroduksi ke beberapa wilayah lain seperti Afrika Selatan, Australia, Selandia Baru, dan Brazil. Boletus edulis dianggap sebagai spesies tipe dari genus Boletus.",
        listOf(
            "Topi: Berwarna coklat muda hingga coklat tua, terkadang dengan semburat merah atau kuning dan Bentuk topi awalnya cembung, kemudian menjadi lebih melebar seiring bertambahnya usia. Permukaan topi bisa halus atau sedikit berkerut.",
            "Bunga: Berwarna putih kekuningan hingga krem saat muda, kemudian berubah menjadi kuning kehijauan seiring bertambahnya usia. Bentuk pori-pori halus dan bulat.",
            "Daging Buah: Berwarna putih, meskipun mungkin memiliki bintik-bintik kecoklatan di dekat pangkal batang dan daging buah tidak berubah warna saat terkena udara.",
            "Daging buah: Berwarna putih, padat, dan tidak berubah warna saat dipotong dan memiliki aroma dan rasa yang harum dan nutty.",
        ),
        listOf(
            "Kaya Nutrisi:\nBoletus edulis, atau cendawan porcini, kaya nutrisi dengan vitamin B1, B2, C, kalium, fosfor, dan magnesium, serta tinggi serat dan protein, cocok bagi vegetarian dan vegan.",
            "Manfaat Antioksidan dan Kekebalan Tubuh:\nSelain itu, kandungannya yang kaya antioksidan membantu melindungi tubuh dari radikal bebas yang bisa menyebabkan stres oksidatif dan berbagai penyakit kronis. Jamur ini juga dapat meningkatkan sistem kekebalan tubuh.",
            "Sifat Anti-inflamasi dan Kesehatan Tubuh:\nBoletus edulis memiliki sifat anti-inflamasi yang membantu mengurangi peradangan dalam tubuh. Peradangan kronis dapat menyebabkan penyakit jantung, kanker, dan arthritis.",
            "Kesehatan Pencernaan:\nKandungan serat tinggi dalam Boletus edulis juga menjaga kesehatan pencernaan dengan melancarkan pencernaan, mencegah sembelit, dan meningkatkan kesehatan usus.",

        )
    ),
    Jamur(
        "phellinus_igniarius",
        "Phellinus Igniarius",
        "Spesies jamur ini merupakan spesies Phellinus Igiarius. Phellinus Igniarius, sering disebut Jamur Api, Willow Bracket, atau False Tinder Conk, adalah jamur polipori yang memiliki peran ganda di alam. Meskipun jamur ini menyebabkan pembusukan kayu pada pohon inang, Phellinus Igniarius juga memiliki potensi manfaat kesehatan yang menarik dan sejarah penggunaan tradisional yang panjang. Meskipun Phellinus Igniarius memiliki potensi manfaat kesehatan, tidak direkomendasikan untuk dimakan. Risiko keracunan, kandungan senyawa berbahaya, dan kurangnya bukti keamanan membuatnya tidak aman untuk dikonsumsi.",
        listOf(
            "Bentuk: Seperti kuku kuda atau kerang dan bagian atas keras dan bergelombang, terkadang ditumbuhi lumut.",
            "Warna: Coklat keabu-abuan hingga hitam lalu bagian bawah memiliki pori-pori halus berwarna putih hingga krem.",
            "Habitat: Tumbuh di batang pohon yang masih hidup atau mati, terutama pohon willow, aspen, dan birch dan biasanya ditemukan di hutan atau daerah berhutan.",
            "Ciri-ciri Lain: Jamur ini tumbuh secara tunggal atau bertumpuk, Bagian dalamnya berwarna coklat kayu, Teksturnya keras dan berserat, dan Memiliki bau yang khas, sedikit pahit.",
        ),
        "Meskipun Phellinus Igniarius memiliki sejarah panjang dalam pengobatan tradisional dan beberapa penelitian menunjukkan potensi manfaat kesehatannya, jamur ini tidak direkomendasikan untuk dimakan karena beberapa alasan. Pertama, ada potensi keberacunan karena sulitnya mengidentifikasi jamur ini dan kemiripannya dengan jamur beracun lainnya. Kandungan senyawa berbahaya seperti asam oksalat dan fenolik juga dapat menyebabkan efek samping seperti batu ginjal dan iritasi pencernaan. "
    ),
    Jamur(
        "pleurotus_ostreatus",
        "Pleurotus Ostreatus",
        "Spesies jamur ini merupakan spesies Pleurotus Ostreatus. Pleurotus Ostreatus, lebih dikenal sebagai Jamur Tiram, adalah jamur populer yang digemari banyak orang karena rasanya yang lezat dan teksturnya yang kenyal. Jamur ini mudah dibudidayakan dan memiliki banyak manfaat kesehatan, menjadikannya pilihan yang tepat untuk pecinta kuliner dan mereka yang ingin hidup sehat.",
        listOf(
            "Topi: Berbentuk kipas atau tidak beraturan, dengan diameter 5-40 cm. Warnanya bervariasi, dari putih, krem, abu-abu, hingga coklat tua. Permukaannya halus atau sedikit berbulu, dengan garis-garis radial yang jelas",
            "Insang: Berwarna putih atau krem, tebal, dan berdekatan.",
            "Batang: Berwarna putih atau krem, kokoh, dan sering kali bercabang.",
            "Habitat: Tumbuh di hutan gugur dan hutan konifer, biasanya pada batang pohon mati atau yang sedang membusuk."
        ),
        listOf(
            "Kaya Nutris:\nJamur Tiram adalah sumber nutrisi penting, mengandung vitamin B kompleks, vitamin C, kalium, dan mineral lainnya seperti selenium, tembaga, dan zinc.",
            "Manfaat Kesehatan Pencernaan dan Kolesterol:\nRendah kalori dan tinggi serat, jamur ini membantu melancarkan pencernaan dan menurunkan kolesterol.",
            "Manfaat Antioksidan:\nKaya akan antioksidan, seperti ergothioneine dan senyawa fenolik, yang melindungi tubuh dari radikal bebas yang dapat menyebabkan penyakit kronis.",
            "Manfaat untuk Kesehatan Tubuh Lainnya:\nSelain itu, jamur ini mungkin meningkatkan sistem kekebalan tubuh, menjaga kesehatan jantung, mengontrol kadar gula darah, dan membantu dalam penurunan berat badan"
        ),
    ),
    Jamur(
        "suillus",
        "Suillus",
        "Spesies jamur ini merupakan genus Suillus. Jamur ini umumnya berasosiasi dengan pohon pinus (Pinaceae) dan banyak ditemukan di daerah beriklim sedang di Belahan Bumi Utara, meskipun beberapa spesies telah diperkenalkan ke Belahan Bumi Selatan. Jamur Suillus, dengan ciri khasnya yang unik, memang bisa dikonsumsi. Namun, penting untuk diingat bahwa konsumsi jamur ini tidak direkomendasikan secara luas. Suillus hidup berdampingan dengan pohon-pohon pinus, dan kebanyakan spesiesnya tersebar di wilayah beriklim sedang di Belahan Bumi Utara. Beberapa spesies bahkan telah diperkenalkan ke Belahan Bumi Selatan.",
        listOf(
            "Topi: bewarna Kuning, oranye, coklat, atau kombinasi warna-warna tersebut. Permukaanya  Lengket atau berlendir saat basah, halus atau sedikit berbulu, dengan garis-garis radial yang jelas. Lalu berbentuk: Kipas, tidak beraturan, atau cembung. berukuran: Diameter 5-40 cm.",
            "Batang: bewarna Putih, krem, atau coklat muda. Dan bertekstur: Kokoh, berserat, dan sering kali bercabang. Polanya Retikulat (seperti jaring). berukuran: Diameter 1-3 cm, tinggi 3-12 cm",
            "Pori-pori: bewarna putih, krem, kuning, atau abu-abu dan bentuk bulat atau oval. Lalu berukuran: Kecil hingga sedang. berlokasi Di bagian bawah topi, bukan insang seperti pada kebanyakan jamur",
            "Daging: bewarna putih atau kuning lalu bertekstur lembut dan kenyal saat muda, menjadi lebih keras dan alot seiring bertambahnya usia."
        ),
       "Jenis jamur Suillus memiliki potensi keracunan karena kemiripannya dengan jamur beracun lainnya seperti Boletus satanas dan Boletus calopus. Gejalanya meliputi mual, muntah, diare, sakit perut, pusing, dan kelemahan. Senyawa berbahaya seperti asam oksalat dan fenolik dapat menyebabkan batu ginjal, iritasi pencernaan, dan interaksi obat. Penelitian tentang keamanannya masih terbatas, sehingga alternatif yang lebih aman seperti jamur kancing, jamur tiram, dan jamur enoki disarankan untuk dikonsumsi."
    ),
    Jamur(
        "hericium_coralloides",
        "Hericium Coralloides",
        "Spesies jamur ini merupakan spesies Hericium Coralloides. Hericium coralloides, sering dijuluki sebagai coral tooth fungus (jamur gigi karang) atau comb coral mushroom (jamur karang sisir) , adalah jamur basidiomycete yang cantik dan menarik. Dikenal dengan penampilannya yang unik menyerupai karang gigi, jamur ini dapat dimakan dan memiliki rasa yang lezat.",
        listOf(
            "Bentuk: Tidak memiliki topi dan insang seperti kebanyakan jamur, Struktur tubuh menyerupai gigi atau icicle (tukul es) yang menjuntai ke bawah, Bentuk gigi-giginya runcing dan panjang, menyerupai terumbu karang berwarna putih hingga krem, Panjang gigi-giginya bisa mencapai 1-5 cm, tergantung pada kematangan jamur.",
            "Batang: : Batang biasanya pendek dan tebal, dengan warna putih hingga krem, Bagian pangkal batang bisa menyatu dengan beberapa cabang, menyerupai bentuk kipas, Tekstur batang umumnya padat dan keras.",
            "Ukuran: Diameter keseluruhan Hericium Coralloides bisa mencapai 5-20 cm.",
            "Habitat: Biasanya tumbuh soliter atau berkelompok kecil di hutan gugur atau hutan berlumut Cenderung menempel pada kayu mati atau batang pohon yang sedang membusuk, Lebih sering ditemukan di daerah beriklim subtropis dan sedang.",
            "Permukaan: Permukaan gigi-giginya biasanya halus dan kering, Terkadang permukaannya bisa sedikit lengket saat lembab."
        ),
        listOf(
            "Sistem Kekebalan Tubuh:\nJamur ini mendukung sistem kekebalan tubuh dengan mengandung beta-glukan, yang merangsang sel-sel kekebalan tubuh untuk melawan infeksi dan penyakit.",
            "Kesehatan Saraf:\nSelain itu, Hericium Coralloides dapat melindungi kesehatan saraf dengan meningkatkan produksi faktor pertumbuhan saraf, yang penting untuk kesehatan otak jangka panjang dan dapat membantu mencegah penyakit neurodegeneratif seperti Alzheimer dan Parkinson.",
            "Sifat Anti-kanker:\nStudi awal juga menunjukkan potensi sifat anti-kanker jamur ini, meskipun penelitian lebih lanjut diperlukan untuk mengkonfirmasi efeknya pada manusia.",
            "Kesehatan Diabetes dan Pencernaan:\nHericium Coralloides dapat membantu menurunkan kadar gula darah pada penderita diabetes, meningkatkan kesehatan pencernaan melalui prebiotiknya, dan mencegah peradangan kronis dengan sifat anti-inflamasi yang dimilikinya."
        ),
    ),
)