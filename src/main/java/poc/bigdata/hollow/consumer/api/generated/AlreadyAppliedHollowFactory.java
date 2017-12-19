package poc.bigdata.hollow.consumer.api.generated;

import com.netflix.hollow.api.objects.provider.HollowFactory;
import com.netflix.hollow.core.read.dataaccess.HollowTypeDataAccess;
import com.netflix.hollow.api.custom.HollowTypeAPI;

@SuppressWarnings("all")
public class AlreadyAppliedHollowFactory<T extends AlreadyApplied> extends HollowFactory<T> {

    @Override
    public T newHollowObject(HollowTypeDataAccess dataAccess, HollowTypeAPI typeAPI, int ordinal) {
        return (T)new AlreadyApplied(((AlreadyAppliedTypeAPI)typeAPI).getDelegateLookupImpl(), ordinal);
    }

    @Override
    public T newCachedHollowObject(HollowTypeDataAccess dataAccess, HollowTypeAPI typeAPI, int ordinal) {
        return (T)new AlreadyApplied(new AlreadyAppliedDelegateCachedImpl((AlreadyAppliedTypeAPI)typeAPI, ordinal), ordinal);
    }

}