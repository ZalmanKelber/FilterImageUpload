package SimpleSBApps.filters.bucket;

public enum BucketName {

    PROFILE_IMAGE("filter-image-upload");

    final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
