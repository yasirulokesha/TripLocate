# 📍 TripLocate App  

**TripLocate** is a user-friendly Android application designed to enhance the travel experience by helping users discover and manage tourist destinations. The app integrates seamlessly with Firebase for efficient data management and provides a responsive and intuitive interface for a smooth user experience.  

---

## 🌟 Features  

- **Add Places**: Users can add new tourist destinations with details and images.  
- **Favorites Management**: Mark and manage favorite locations for quick access.  
- **Detail View**: View detailed information about each location, including images and descriptions.  
- **Responsive Design**: Optimized layouts for a consistent experience across various screen sizes.  
- **Firebase Integration**: Efficient data storage and retrieval using Firebase Firestore and Firebase Storage.  
- **Smooth Navigation**: Constraint-based layouts and RecyclerView ensure seamless app navigation and data display.  

---

## 🛠️ Tech Stack  

- **Language**: Kotlin  
- **Architecture**: MVVM (Model-View-ViewModel)  
- **Backend**: Firebase Firestore & Firebase Storage  
- **UI Components**: RecyclerView, ConstraintLayout, Custom XML layouts  
- **Tools**: Android Studio, Gradle  

---

## 📂 Project Structure  

📦 TripLocate App  
├─ app/  
│  ├─ src/main/  
│  │  ├─ java/app/triplocate/  
│  │  │  ├─ AddPlace.kt  
│  │  │  ├─ Data.kt  
│  │  │  ├─ DetailView.kt  
│  │  │  ├─ Favourites.kt  
│  │  │  ├─ MainActivity.kt  
│  │  │  ├─ RecyclerAdapter.kt  
│  │  │  └─ ui/theme/  
│  │  │     ├─ Color.kt  
│  │  │     ├─ Theme.kt  
│  │  │     └─ Type.kt  
│  │  ├─ res/  
│  │  │  ├─ layout/  
│  │  │  │  ├─ activity_detail_view.xml  
│  │  │  │  ├─ add_place.xml  
│  │  │  │  ├─ bottom_app_bar.xml  
│  │  │  │  ├─ favourites_layout.xml  
│  │  │  │  ├─ home_layout_.xml  
│  │  │  │  ├─ item_container.xml  
│  │  │  │  └─ main_activity.xml  
│  │  │  └─ drawable/  
│  │  │     ├─ ic_add.xml  
│  │  │     ├─ ic_favorite.xml  
│  │  │     ├─ ic_home.xml  
│  │  │     └─ sample_img.png  
└─ ...

## 🚀 Getting Started  

### Prerequisites  
- Android Studio installed.  
- Firebase account for integrating Firestore and Storage.  

### Setup  
1. Clone the repository:  
   ```bash  
   git clone https://github.com/yourusername/TripLocate.git  
   
2. Open the project in Android Studio.
3. Add your google-services.json file in the app/ directory.
4. Sync Gradle files.
5. Run the app on an emulator or a physical device.

## 🙌 Acknowledgments
- Firebase for backend services.
- Android Studio for development tools.
- Icons by Material Design Icons.
