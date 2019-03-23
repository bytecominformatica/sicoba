class Snapshot {
    constructor({data = null, error = null} = {}) {
        this.data = data;
        this.error = error;
    }

    hasData = () => this.data != null;
    hasError = () => this.error != null;

}

export default Snapshot;