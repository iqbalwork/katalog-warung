# 📦 Aplikasi Katalog Produk Warung

Aplikasi Android untuk katalog produk warung dengan fitur pencatatan produk, pencarian, serta manajemen harga. Dibangun menggunakan Jetpack Compose dan Room (offline database) dengan arsitektur MVVM.

## 🚀 Fitur Utama
- ✅ Tambah, edit, hapus produk
- 🖼️ Gambar dari kamera atau galeri
- 🔍 Pencarian produk berdasarkan nama
- 📦 Penyimpanan lokal menggunakan Room (tanpa login)
- ☁️ Sinkronisasi dengan Firebase Realtime Database (berbasis Google Sheets)

## 🛠️ Teknologi yang Digunakan
- Jetpack Compose
- Kotlin Coroutines & Flow
- Room Database
- Firebase Realtime Database (opsional)
- Firebase Storage (opsional)
- MVVM + Repository Pattern

## 📄 Struktur Data Produk
- `id`: String (UUID)
- `name`: String
- `category`: String
- `costPrice`: Double
- `sellPrice`: Double
- `imageUri`: String (lokal atau URL)
- `timestamp`: Long

## 🔧 Status
📱 Dalam tahap pengembangan awal

---
