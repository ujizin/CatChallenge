# Journey

Hi! This page provides an overview of how the project has been developed, along with the challenges
faced and solutions implemented for each release.

## Development Strategy

The development strategy for the app includes:

- Using [The CatAPI](https://thecatapi.com/) as the data provider for the project.
- Building the stack based on Modern Android Development (MAD) principles.

## Release 0.1.0

### Architecture overview

The project is currently structured as a single module, organized into packages. A single module was
chosen for now to speed up the delivery of the first MVP version.

The architecture follows a reactive programming model
with [UDF](https://developer.android.com/topic/architecture/ui-layer#udf).

#### Overview:

- **core**
    - **data**: Manages the data layer, handling data sources (e.g., APIs, databases) and utilizing
      coroutines and flows to ensure reactive and asynchronous.
    - **navigation**: Defines the navigation structure, bottom navigation bar and destinations
    - **ui**: Provides UI components and UI models as the foundation
      **test**: Contains utilities and helpers for unit testing.
- **feature**
    - **home**: Main screen functionality with cat's breeds paging
    - **breeddetail**: Detailed breed information
    - **favorite**:  Manages the functionality to the user's favorite breeds

### Issues & Solutions

#### Favorite - Unique User vs Shared User

Initially, the first attempt was not using the `sub_id` field in the Favorite RESTful API. However,
it was observed that favorites were being shared across devices.

To resolve this issue, It was implemented the use of the Android ID (hardware) to ensure that
favorites remain unique to each user.

**Side Benefit**: When the app is re-downloaded on the device, the favorites are restored.

---

#### Breed - Image Interceptor Endpoint

The initial implementation used the endpoint `/v1/images/{imageId}` to retrieve the image URL for
cats. However, this approach resulted in slow data loading times for the app.

To address this issue, we observed that the image URLs from `/v1/images/{imageId}` follow a specific
pattern, differing only in their suffixes. For example:

```
// imageId = "abcde"

"/v1/images/abcde.jpg"
"/v1/images/abcde.png"
```

In most cases, the images are served in the `.jpg` format. With this knowledge, I developed a
solution by creating an ImageInterceptor.

The approach for the interceptor is as follows: when the request is for a CDN URL, it first attempts
to retrieve the image with the `.jpg` suffix. If this fails, the interceptor then tries the `.png`
suffix.

---

#### Favorite - ImageId is the new breedId

In the CatAPI Favorites RESTful API, when posting a favorite, it's possible to include two fields in
the body:

```
{
    "image_id": "hBXicehMA", // required
    "sub_id": "my-user-1234" // Optional, used for user Id in Application
}
```

However, some breed items do not have a `reference_image_id` (for example, the "European Burmese"),
which means that `image_id` cannot be used in this case. To address this, `breedId` was used instead
of `imageId`.

Note: I tried to use POST `v1/images/upload` but I could not making it works, the error was:

```
 Classifcation failed: correct animal not found. - Bad Request
```

As a result, it was switched to using `breedId`. Moreover, even with manual POST requests, the user
experience when a new `API_KEY` is used is not ideal, as users cannot upload photos.

---

#### Paging - Remote Mediator

This was my first experience using Remote Mediator with Paging 3. Overall, I found it to be overly
complex for simply loading pages. Additionally, the documentation often lacks examples for API
scenarios that do not use a cursor-based paging strategy or an ascending ID.

--- 

#### Favorite - Concurrency on API

When users tap the Favorite button too quickly, it can lead to data loss, resulting in gaps in the
Cat API. To address this issue, a `mutex` was implemented to manage concurrent access.

Initially, the UI was updated immediately after the tap on presentation, however, this approach
sometimes led to misinformation being displayed to the user. As a result, the implementation on the
presentation layer (UI) was removed.
