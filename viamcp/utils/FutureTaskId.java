package viamcp.utils;

import com.viaversion.viaversion.api.platform.*;
import java.util.concurrent.*;

public class FutureTaskId implements PlatformTask<Future<?>>
{
    private final Future<?> object;
    
    public FutureTaskId(final Future<?> object) {
        this.object = object;
    }
    
    @Override
    public Future<?> getObject() {
        return this.object;
    }
    
    @Override
    public void cancel() {
        this.object.cancel(false);
    }
}
