package sg.edu.np.mad.snatch;

interface orderItemAdapterCallback {
    public void removeOrderItem(OrderItem item, int position);

    public void removeAllOrderItems(int position);
}
